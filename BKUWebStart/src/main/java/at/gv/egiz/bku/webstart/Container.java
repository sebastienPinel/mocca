/*
 * Copyright 2011 by Graz University of Technology, Austria
 * MOCCA has been developed by the E-Government Innovation Center EGIZ, a joint
 * initiative of the Federal Chancellery Austria and Graz University of Technology.
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * http://www.osor.eu/eupl/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * This product combines work with different licenses. See the "NOTICE" text
 * file for details on the various modules and licenses.
 * The "NOTICE" text file is part of the distribution. Any derivative works
 * that you distribute must include a readable copy of the "NOTICE" text file.
 */


package at.gv.egiz.bku.webstart;

import iaik.utils.StreamCopier;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Locale;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.SslSocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Container {

  public static final String HTTP_PORT_PROPERTY = "mocca.http.port";
  public static final String HTTPS_PORT_PROPERTY = "mocca.https.port";

  private static final String JETTY_TEMP_STORE = ".jettytemp";

  private static Logger log = LoggerFactory.getLogger(Container.class);

  static {
    if (log.isDebugEnabled()) {
      //Jetty log INFO and WARN, include ignored exceptions
      //jetty logging may be further restricted by setting level in log4j.properties
      System.setProperty("VERBOSE", "true");
      //do not set Jetty DEBUG logging, produces loads of output
      //System.setProperty("DEBUG", "true");
    }
  }
  private Server server;
  private WebAppContext webapp;
  private WebappErrorHandler errorHandler;
  private Certificate caCertificate;
  private File tempDir;

  private Locale locale = null;

  public void init(Locale locale) throws IOException {
    this.locale = locale;
    init();
  }

  public void init() throws IOException {
//    System.setProperty("DEBUG", "true");
    deleteJettyTemp();
    server = new Server();
    QueuedThreadPool qtp = new QueuedThreadPool();
    qtp.setMaxThreads(5);
    qtp.setMinThreads(2);
    qtp.setLowThreads(0);
    server.setThreadPool(qtp);
    server.setStopAtShutdown(true);
    server.setGracefulShutdown(3000);

    SelectChannelConnector connector = new SelectChannelConnector();
    connector.setPort(Integer.getInteger(HTTP_PORT_PROPERTY, 3495).intValue());
    connector.setAcceptors(1);
    connector.setConfidentialPort(Integer.getInteger(HTTPS_PORT_PROPERTY, 3496).intValue());
    connector.setHost("127.0.0.1");

    SslSocketConnector sslConnector = new SslSocketConnector();
    sslConnector.setPort(Integer.getInteger(HTTPS_PORT_PROPERTY, 3496).intValue());
    sslConnector.setAcceptors(1);
    sslConnector.setHost("127.0.0.1");
    File configDir = new File(System.getProperty("user.home") + "/" + Configurator.CONFIG_DIR);
    File keystoreFile = new File(configDir, Configurator.KEYSTORE_FILE);
    if (!keystoreFile.canRead()) {
      log.error("MOCCA keystore file not readable: " + keystoreFile.getAbsolutePath());
      throw new FileNotFoundException("MOCCA keystore file not readable: " + keystoreFile.getAbsolutePath());
    }
    log.debug("loading MOCCA keystore from " + keystoreFile.getAbsolutePath());
    sslConnector.setKeystore(keystoreFile.getAbsolutePath());
    String passwd = readPassword(new File(configDir, Configurator.PASSWD_FILE));
    sslConnector.setPassword(passwd);
    sslConnector.setKeyPassword(passwd);

    server.setConnectors(new Connector[]{connector, sslConnector});

    webapp = new WebAppContext();
    webapp.setLogUrlOnStart(true);
    webapp.setContextPath("/");
    webapp.setExtractWAR(true);
    webapp.setParentLoaderPriority(false);

    errorHandler = new WebappErrorHandler(locale);
    webapp.setErrorHandler(errorHandler);

    tempDir = webapp.getTempDirectory();
    webapp.setWar(copyWebapp(tempDir));
//    webapp.setPermissions(getPermissions(tempDir));

    server.setHandler(webapp);
    server.setGracefulShutdown(1000 * 3);

    loadCACertificate(keystoreFile, passwd.toCharArray());
  }

  /**
   * @return The first valid (not empty, no comment) line of the passwd file
   * @throws IOException
   */
  protected static String readPassword(File passwdFile) throws IOException {
    if (passwdFile.exists() && passwdFile.canRead()) {
      BufferedReader passwdReader = null;
      try {
        passwdReader = new BufferedReader(new FileReader(passwdFile));
        String passwd;
        while ((passwd = passwdReader.readLine().trim()) != null) {
          if (passwd.length() > 0 && !passwd.startsWith("#")) {
            return passwd;
          }
        }
      } catch (IOException ex) {
        log.error("failed to read password from " + passwdFile, ex);
        throw ex;
      } finally {
        try {
          passwdReader.close();
        } catch (IOException ex) {
        }
      }
    }
    throw new IOException(passwdFile + " not readable");
  }

  private String copyWebapp(File webappDir) throws IOException {
    File webapp = new File(webappDir, "BKULocal.war");
    log.debug("copying BKULocal classpath resource to " + webapp);
    InputStream is = getClass().getClassLoader().getResourceAsStream("BKULocal.war");
    OutputStream os = new BufferedOutputStream(new FileOutputStream(webapp));
    new StreamCopier(is, os).copyStream();
    os.close();
    return webapp.getPath();
  }

  private static boolean deleteRecursive(File f) {
    if (f.isDirectory()) {
      String[] children = f.list();
      for (String child : children) {
        if (!deleteRecursive(new File(f, child)))
          return false;
      }
    }
    return f.delete();
  }

  private static void clean(File tmpDir) {
    log.debug("Trying to remove " + tmpDir);
    if (deleteRecursive(tmpDir)) {
      log.debug("Successfully removed temporary directory");
    }
  }

  private void storeJettyTemp() {
    String os = System.getProperty("os.name");
    if (!os.toLowerCase().contains("windows"))
      return;
    try {
      File userDir = new File(System.getProperty("user.home") + "/" + Configurator.BKU_USER_DIR);
      if (!userDir.exists())
      {
        log.error("User directory " + userDir + " not found");
        return;
      }
      File jettytempstore = new File(userDir, JETTY_TEMP_STORE);
      PrintWriter w = new PrintWriter(jettytempstore, "UTF-8");
      w.println(tempDir.getAbsolutePath());
      w.close();
      log.debug("Stored jetty temp dir " + tempDir.getAbsolutePath() + " for removal on next start");
    } catch (IOException e) {
      log.error("Failed to copy jetty temp cleaner", e);
      e.printStackTrace();
    }
  }

  private void deleteJettyTemp() {
    String os = System.getProperty("os.name");
    if (!os.toLowerCase().contains("windows"))
      return;
    try {
      File userDir = new File(System.getProperty("user.home") + "/" + Configurator.BKU_USER_DIR);
      if (!userDir.exists())
      {
        log.error("User directory " + userDir + " not found");
        return;
      }
      File jettytempstore = new File(userDir, JETTY_TEMP_STORE);
      if (!jettytempstore.exists())
      {
        log.debug("No Jetty temp store file found");
        return;
      }
      BufferedReader r = new BufferedReader(new FileReader(jettytempstore));
      File oldTemp = new File(r.readLine());
      if (oldTemp.exists()) {
        log.info("Deleting old jetty temp dir " + oldTemp);
        clean(oldTemp);
      } else {
        log.debug("Old jetty temp dir " + oldTemp + " doesn't exist anymore");
      }
      r.close();
      jettytempstore.delete();
    } catch (IOException e) {
      log.error("Failed to copy jetty temp cleaner", e);
      e.printStackTrace();
    }
  }

//  /**
//   * grant all permissions, since we need read/write access to save signature data files anywhere (JFileChooser) in the local filesystem
//   * and Jetty does not allow declare (webapp) permissions on a codeBase basis.
//   * @param webappDir
//   * @return
//   */
//  private Permissions getPermissions(File webappDir) {
//    Permissions perms = new Permissions();
//    perms.add(new AllPermission());
////      perms.add(new FilePermission(new File(System.getProperty("user.home")).getAbsolutePath(), "read, write"));
////      perms.add(new FilePermission(new File(System.getProperty("user.home") + "/-").getAbsolutePath(), "read, write"));
////      perms.add(new FilePermission(new File(System.getProperty("user.home") + "/.mocca/logs/*").getAbsolutePath(), "read, write,delete"));
////      perms.add(new FilePermission(new File(System.getProperty("user.home") + "/.mocca/certs/-").getAbsolutePath(), "read, write,delete"));
//
////    perms.add(new FilePermission("<<ALL FILES>>", "read, write"));
//
//    return perms;
//  }

  public void start() throws Exception {
    server.start();
    File caCertFile = new File(webapp.getTempDirectory(), "webapp/ca.crt");
    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(caCertFile));
    bos.write(caCertificate.getEncoded());
    bos.flush();
    bos.close();
  }

  public boolean isRunning() {
    return server.isRunning();
  }

  public void stop() throws Exception {
    server.stop();
    storeJettyTemp();
  }

  public void destroy() {
    server.destroy();
}

  public void join() throws InterruptedException {
    server.join();
  }

  private void loadCACertificate(File keystoreFile, char[] passwd) {
    caCertificate = getCACertificate(keystoreFile, passwd);
    if (caCertificate == null)
      log.warn("automated web certificate installation will not be available");
  }

  protected static Certificate getCACertificate(File keystoreFile, char[] passwd) {
    try {
      if (log.isTraceEnabled()) {
        log.trace("local ca certificate from " + keystoreFile);
      }
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(keystoreFile));
      KeyStore sslKeyStore = KeyStore.getInstance("JKS");
      sslKeyStore.load(bis, passwd);
      Certificate[] sslChain = sslKeyStore.getCertificateChain(TLSServerCA.MOCCA_TLS_SERVER_ALIAS);
      bis.close();
      return sslChain[sslChain.length - 1];
    } catch (Exception ex) {
      log.error("Failed to load local ca certificate", ex);
      return null;
    }
  }
}
