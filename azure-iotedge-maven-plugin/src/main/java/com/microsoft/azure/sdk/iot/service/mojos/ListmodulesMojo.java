/**
 * 
 */
package com.microsoft.azure.sdk.iot.service.mojos;

import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import com.google.gson.JsonSyntaxException;
import com.microsoft.azure.sdk.iot.service.Module;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;

/**
 * @author jurgenma
 *
 */
@Mojo( name = "list-modules")
public class ListmodulesMojo extends AbstractIoTEdgeMojo {

	/* (non-Javadoc)
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		
		RegistryManager registryManager = this.getRegistryManager();
		
		List<Module> modules = null;
		try {
			modules = registryManager.getModulesOnDevice(this.getDeviceId());
		} catch (JsonSyntaxException | IOException | IotHubException e) {
			throw new MojoExecutionException("Could not read modules of device " + this.getDeviceId(), e);
		}
		
		int counter = 1;
		for(Module module : modules) {
			this.getLog().info("Module " + counter +": '" + module.getId() + "'");
			counter++;
		}
	}
}
