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

import iaik.pki.PKIProfile;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.FactoryBean;

import at.gv.egiz.bku.conf.MoccaConfigurationFacade;

public class SSLSocketFactoryBean implements FactoryBean {
  
  protected PKIProfile pkiProfile;
  
  /**
   * The configuration facade.
   */
  protected final ConfigurationFacade configurationFacade = new ConfigurationFacade();
  
  public class ConfigurationFacade implements MoccaConfigurationFacade {
    
    private Configuration configuration;
    
    public static final String SSL_PROTOCOL = "SSL.sslProtocol";
    
    public static final String SSL_DISSABLE_ALL_CHECKS = "SSL.disableAllChecks";
    
    public String getSslProtocol() {
      return configuration.getString(SSL_PROTOCOL, "TLS");
    }
    
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
  public Object getObject() throws Exception {
    
    PKITrustManager pkiTrustManager = new PKITrustManager();
    pkiTrustManager.setConfiguration(configurationFacade.configuration);
    pkiTrustManager.setPkiProfile(pkiProfile);
    
    SSLContext sslContext = SSLContext.getInstance(configurationFacade.getSslProtocol());
    sslContext.init(null, new TrustManager[] {pkiTrustManager}, null);
    
    return sslContext.getSocketFactory();
  }

  @Override
  public Class<?> getObjectType() {
    return SSLSocketFactory.class;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }

}
