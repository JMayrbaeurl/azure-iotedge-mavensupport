# Azure IoT Edge Maven plugin

Maven plugin to work with [Azure IoT Edge](https://azure.microsoft.com/en-us/services/iot-edge/). It uses the [Azure IoT Services Client SDK](https://github.com/Azure/azure-iot-sdk-java/tree/master/service) to manipulate and read the modules configuration of an Azure IoT Edge device.

Using from command line

```
mvn azure-iotedge:list-modules -DIotHubConnectionString="<Insert your IoT Hub connection string (iothubowner)>"
```

with the following setup in your pom.xml file. Replace "deviceId" and "deploymentmanifestfilepath":

```
	<build>
		<plugins>
			<plugin>
				<groupId>com.microsoft.azure.sdk.iot</groupId>
				<artifactId>azure-iotedge-maven-plugin</artifactId>
				<version>1.0-SNAPSHOT</version>
				<configuration>
					<IotHubConnectionString>${IotHubConnectionString}</IotHubConnectionString>
					<deviceId>jurgenmaEdgeP50</deviceId>
					<deploymentmanifestfilepath>C:\Dev\iotedge\azure-iotedge-maven-plugin-test\config\deployment.json</deploymentmanifestfilepath>
				</configuration>
			</plugin>

```

## goal 'list-modules'

Lists all modules currently used on the device specified in the configuration. Conforms to [Service - Get modules on Edge device](https://docs.microsoft.com/en-us/rest/api/iothub/service/getmodulesondevice)

## goal 'set-modules'

Applies the modules configuration in the file specified in "deploymentmanifestfilepath" on the device specified in the configuration. This is the same functionality you can access in the Azure Portal with the 'Set modules' blade and conforms to the [Service - Apply configuration on Edge device](https://docs.microsoft.com/en-us/rest/api/iothub/service/applyconfigurationonedgedevice)