/*
* Copyright 2009 Federal Chancellery Austria and
* Graz University of Technology
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package at.gv.egiz.bku.spring;

import iaik.logging.TransactionId;
import iaik.pki.PKIException;
import iaik.pki.PKIFactory;
import iaik.pki.PKIModule;
import iaik.pki.PKIProfile;
import iaik.pki.store.truststore.TrustStore;
import iaik.pki.store.truststore.TrustStoreException;
import iaik.pki.store.truststore.TrustStoreFactory;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.X509TrustManager;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import at.gv.egiz.bku.conf.MoccaConfigurationFacade;

public class PKITrustManager implements X509TrustManager {
  
  Logger log = LoggerFactory.getLogger(PKITrustManager.class);

  protected PKIProfile pkiProfile;

  /**
   * The configuration facade.
   */
  protected final ConfigurationFacade configurationFacade = new ConfigurationFacade();
  
  public class ConfigurationFacade implements MoccaConfigurationFacade {
    
    private Configuration configuration;
    
    public static final String SSL_DISSABLE_ALL_CHECKS = "SSL.disableAllChecks";
    
    public boolean disableAllSslChecks() {
      return configuration.getBoolean(SSL_DISSABLE_ALL_CHECKS, false);
    }
    
  }

  /**
   * @return the configuration
   */
  public Configuration getConfiguration() {
    return configurationFacade.configuration;
  }

  /**
   * @param configuration the configuration to set
   */
  public void setConfiguration(Configuration configuration) {
    configurationFacade.configuration = configuration;
  }
  
  /**
   * @return the pkiProfile
   */
  public PKIProfile getPkiProfile() {
    return pkiProfile;
  }

  /**
   * @param pkiProfile the pkiProfile to set
   */
  public void setPkiProfile(PKIProfile pkiProfile) {
    this.pkiProfile = pkiProfile;
  }

  @Override
  public void checkClientTrusted(X509Certificate[] chain, String authType)
      throws CertificateException {
    checkServerTrusted(chain, authType);
  }

  @Override
  public void checkServerTrusted(X509Certificate[] chain, String authType)
      throws CertificateException {

    if (pkiProfile == null) {
      throw new CertificateException("No PKI profile set. Configuration error.");
    }
    
    if (configurationFacade.disableAllSslChecks()) {
      log.warn("SSL certificate validation disabled. " +
      		"Accepted certificate {}.", chain[0].getSubjectDN());
    } else {

      iaik.x509.X509Certificate[] certs = convertCerts(chain);
      
      TransactionId tid = new MDCTransactionId();    
      try {
        PKIModule pkiModule = PKIFactory.getInstance().getPKIModule(pkiProfile);
        if (!pkiModule.validateCertificate(new Date(), certs[0], certs, null,
            tid).isCertificateValid()) {
          throw new CertificateException("Certificate not valid.");
        }
      } catch (PKIException e) {
        log.warn("Failed to validate certificate.", e);
        throw new CertificateException("Failed to validate certificate. " + e.getMessage());
      }
      
    }

  }

  @Override
  public X509Certificate[] getAcceptedIssuers() {
    
    if (pkiProfile == null) {
      log.warn("No PKI profile set. Configuration error.");
      return new X509Certificate[] {};
    }
    
    TransactionId tid = new MDCTransactionId();
    
    try {
      
      TrustStore trustStore = TrustStoreFactory.getInstance(pkiProfile.getTrustStoreProfile(), tid);
      return (X509Certificate[]) trustStore.getTrustedCertificates(tid).toArray();
      
    } catch (TrustStoreException e) {
      log.warn("Failed to get list of accepted issuers.", e);
      return new X509Certificate[] {};
    }

  }

  private static iaik.x509.X509Certificate[] convertCerts(
      X509Certificate[] certs) throws CertificateException {
    iaik.x509.X509Certificate[] retVal = new iaik.x509.X509Certificate[certs.length];
    int i = 0;
    for (X509Certificate cert : certs) {
      if (cert instanceof iaik.x509.X509Certificate) {
        retVal[i++] = (iaik.x509.X509Certificate) cert;
      } else {
        retVal[i++] = new iaik.x509.X509Certificate(cert.getEncoded());
      }
    }
    return retVal;
  }

  private static class MDCTransactionId implements TransactionId {
    @Override
    public String getLogID() {
      String sessionId = MDC.get("SessionId");
      return (sessionId != null) ? sessionId : "PKITrustManager"; 
    }
  }
}
