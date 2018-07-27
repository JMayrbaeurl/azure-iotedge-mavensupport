/**
 * 
 */
package com.microsoft.azure.sdk.iot.service.mojos;

import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import com.microsoft.azure.sdk.iot.service.RegistryManager;

/**
 * @author jurgenma
 *
 */
public abstract class AbstractIoTEdgeMojo extends AbstractMojo {

	@Parameter( property = "IotHubConnectionString", required = true)
	private String IotHubConnectionString;
	
	@Parameter( property = "deviceId", required = true)
	private String deviceId;
	
	private RegistryManager registryManager;

	/**
	 * @return
	 * @throws MojoExecutionException
	 */
	protected RegistryManager getRegistryManager() throws MojoExecutionException {
		
		if (this.registryManager == null) {
			try {
				this.getLog().debug("Creating registry manager with IoT Hub connection string: '"
						+ this.IotHubConnectionString + "'");
				
				this.registryManager = RegistryManager.createFromConnectionString(this.IotHubConnectionString);
			} catch (IOException e) {
				throw new MojoExecutionException("Could not create RegistryManager from connection string", e);
			}
		}
		
		return this.registryManager;
	}
	
	/**
	 * @return the iotHubConnectionString
	 */
	public String getIotHubConnectionString() {
		return IotHubConnectionString;
	}

	/**
	 * @param iotHubConnectionString the iotHubConnectionString to set
	 */
	protected void setIotHubConnectionString(String iotHubConnectionString) {
		IotHubConnectionString = iotHubConnectionString;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	protected void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
