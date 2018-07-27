/**
 * 
 */
package com.microsoft.azure.sdk.iot.service.mojos;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.microsoft.azure.sdk.iot.deps.serializer.ConfigurationContentParser;
import com.microsoft.azure.sdk.iot.service.ConfigurationContent;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;

/**
 * @author jurgenma
 *
 */
@Mojo ( name = "set-modules" )
public class SetmodulesMojo extends AbstractIoTEdgeMojo {

	@Parameter(property = "deploymentmanifestfilepath", required = true)
	private String deploymentmanifestfilepath;
	
	/* (non-Javadoc)
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		
		RegistryManager registryManager = this.getRegistryManager();
		
		ConfigurationContent content;
		try {
			this.getLog().debug("Reading deployment configuration for device '"
					+ this.getDeviceId() + "' from file at '" + this.deploymentmanifestfilepath + "'");
			
			content = this.readDeploymentConfigurationFromFile();
			if (content == null || content.getModulesContent() == null) {
				throw new IllegalArgumentException("Deployment manifest file at '"
						+ this.deploymentmanifestfilepath + "' doesn't contain valid configuration information");
			}
		} catch (IOException e) {
			throw new MojoExecutionException("Could not read deployment manifest file at " + this.deploymentmanifestfilepath, e);
		}
		
		try {
			this.getLog().debug("Calling REST API of Registry Manager to apply new configuration");
			registryManager.applyConfigurationContentOnDevice(this.getDeviceId(), content);
			this.getLog().info("Successfully set modules for device '" + this.getDeviceId() 
				+ "' with configuration from '" + this.deploymentmanifestfilepath + "'");
		} catch (IOException | IotHubException e) {
			throw new MojoExecutionException("Could not set modules of device " + this.getDeviceId(), e);
		}
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	protected ConfigurationContent readDeploymentConfigurationFromFile() throws IOException {
		
		byte[] encoded = Files.readAllBytes(Paths.get(this.deploymentmanifestfilepath));
		String jsonString = new String(encoded, Charset.forName("UTF-8"));
		
		ConfigurationContentParser parser = new ConfigurationContentParser(jsonString);
		ConfigurationContent result = new ConfigurationContent();
		result.setDeviceContent(parser.getDeviceContent());
		result.setModulesContent(parser.getModulesContent());
		
		return result;
	}

	/**
	 * @return the deploymentmanifestfilepath
	 */
	public final String getDeploymentmanifestfilepath() {
		return deploymentmanifestfilepath;
	}

	/**
	 * @param deploymentmanifestfilepath the deploymentmanifestfilepath to set
	 */
	protected final void setDeploymentmanifestfilepath(String deploymentmanifestfilepath) {
		this.deploymentmanifestfilepath = deploymentmanifestfilepath;
	}

}
