# Platform Architecture

#### by [Jakob Feistenauer](https://github.com/yescob) & [Marcel Nague](https://github.com/marcel-nague)

Detailed overview of the architecture of the Layblar Platform

## System Architecture

<img src="img/LayblarServiceArchitecture.drawio.svg" alt="Layblar Microservice Architecture" style="height: 550px">

## Domain Model

<!-- <img src="https://layblar.github.io/platform/img/LayblarDomainModel.png" alt="Layblar Domain Model"> -->
<img src="img/LayblarDomainModel.png" alt="Layblar Domain Model" style="height: 550px">

## Services and System Operations

| Service              | System Operation                                          | Collaborators                                                                                                |
| -------------------- | --------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------ |
| HouseholdService          | [createUser()](#createuser)                               | <ul><li>HouseholdService::createHousehold()</li></ul>                                                             |
| HouseholdService          | [loginUser()](#loginuser)                                 | <ul><li>HouseholdService::verifyUser()</li></ul>                                                                  |
| HouseholdService          | [joinHousehold()](#joinhousehold)                         | <ul><li>HouseholdService::verifyUser()</li><li>HouseholdService::mergeHouseholds()</li></ul>                           |
| HouseholdService          | [leaveHousehold()](#leavehousehold)                       | <ul><li>HouseholdService::verifyUser()</li><li>HouseholdService::splitHouseholds()</li></ul>                           |
| HouseholdService          | [getHouseholdInformation()](#gethouseholdinformation)     | <ul><li>HouseholdService::verifyUser()</li></ul>                                                                  |
| HouseholdService          | [addDeviceToHousehold()](#adddevicetohousehold)           | <ul><li>HouseholdService::verifyUser()</li><li>DeviceLibraryService::addDeviceInformation()</li></ul>             |
| HouseholdService          | [updateDeviceInformation()](#updatedeviceinformation)     | <ul><li>HouseholdService::verifyUser()</li><li>DeviceLibraryService::updateDeviceInformation()</li></ul>          |
| HouseholdService          | [removeDeviceFromHousehold()](#removedevicefromhousehold) | <ul><li>HouseholdService::verifyUser()</li></ul>                                                                  |
| HouseholdService          | [listHouseholdDevices()](#listhouseholddevices)           | <ul><li>HouseholdService::verifyUser()</li><li>DeviceLibraryService::findDeviceInformation()</li></ul>            |
| HouseholdService          | [registerSmartMeterReader()](#registersmartmeterreader)   | <ul><li>HouseholdService::verifyUser()</li><li>SmartMeterService::addNewSmartMeter()</li></ul>                    |
| HouseholdService          | [removeSmartMeterReader()](#removesmartmeterreader)       | <ul><li>HouseholdService::verifyUser()</li><li>SmartMeterService::removeSmartMeter()</li></ul>                    |
| SmartMeterService    | [getSmartMeterData()](#getsmartmeterdata)                 | <ul><li>HouseholdService::verifyUser()</li></ul>                                                                  |
| ProjectService       | [createResearcher()](#createresearcher)                   |                                                                                                              |
| ProjectService       | [createProject()](#createproject)                         | <ul><li>ProjectService:verifyResearcher()</li></ul>                                                          |
| ProjectService       | [updateProjectInformation()](#updateprojectinformation)   | <ul><li>ProjectService:verifyResearcher()</li><li>ProjectService:verifyProjectInformationDetails()</li></ul> |
| ProjectService       | [updateProjectSettings()](#updateprojectsettings)         | <ul><li>ProjectService:verifyResearcher()</li><li>ProjectService:verifyProjectSettingsDetails()</li></ul>    |
| ProjectService       | [listProjects()](#listprojects)                           | <ul><li>HouseholdService::verifyUser()</li><li>HouseholdService::verifyResearcher()</li></ul>                          |
| ProjectService       | [joinProject()](#joinproject)                             | <ul><li>HouseholdService::verifyUser()</li></ul>                                                                  |
| ProjectService       | [getProjectInformation()](#getprojectinformation)         | <ul><li>HouseholdService::verifyUser()</li></ul>                                                                  |
| LabelService         | [getProjectLabels()](#getprojectlabels)                   | <ul><li>HouseholdService::verifyUser()</li></ul>                                                                  |
| LabelService         | [createLabel()](#createlabel)                             | <ul><li>HouseholdService::verifyUser()</li><li>DeviceLibraryService::findDeviceInformation()</li></ul>            |
| DeviceLibraryService | [listDevices()](#listdevices)                             |                                                                                                              |

## Create User {createuser}

**Description** : Create a new user.

**Operation** : createUser(firstname, lastname, email, password)

**Returns** : Household Information

**Preconditions** :

- None

**Postconditions** :

- A new user was successfully created.
- A new household was successfully created.
- The new user is part of the new household

**Collaborators** :

## Login User {loginuser}

**Description** : Allow a user to log in.

**Operation** : loginUser(email, password)

**Returns** : Authentication token

**Preconditions** :

- User exists and is part of a household.
- The password is correct.

**Postconditions** :

- The user is authenticated and logged in.

**Collaborators** :

## Join Household {joinhousehold}

**Description** : Allow a user to join a household.

**Operation** : joinHousehold(userId, householdId)

**Returns** : Success status

**Preconditions** :

- User verification is successful.
- The household to be joined exists.
- Merging households, if necessary, is successful.

**Postconditions** :

- The user has joined the household.

**Collaborators** :

- HouseholdService::verifyUser()
- HouseholdService::mergeHouseholds()

## Leave Household {leavehousehold}

**Description** : Allow a user to leave a household.

**Operation** : leaveHousehold(userId, householdId)

**Returns** : A new household with only the user as a member

**Preconditions** :

- User verification is successful.
- The user is a member of the household.
- Splitting households, if necessary, is successful.

**Postconditions** :

- The user has left the household.
- The user is part of a new single household

**Collaborators** :

- HouseholdService::verifyUser()
- HouseholdService::splitHouseholds()

## Get Household Information {gethouseholdinformation}

**Description** : Retrieve information about a user's household.

**Operation** : getHouseholdInformation(householdId)

**Returns** : Household information

**Preconditions** :

- User verification is successful.
- User is part of the household.

**Postconditions** :

- Household information is retrieved.

**Collaborators** :

- HouseholdService::verifyUser()

## Add Device to Household {adddevicetohousehold}

**Description** : Add a device to a household.

**Operation** : addDeviceToHousehold(deviceInformation)

**Returns** : Confirmation that device has been added.

**Preconditions** :

- User verification is successful.
- Device information is provided and valid.

**Postconditions** :

- The device is added to the household.
- The device information is added to the device library.

**Collaborators** :

- HouseholdService::verifyUser()
- DeviceLibraryService::addDeviceInformation()

## Update Device Information {updatedeviceinformation}

**Description** : Update information about a device in a household.

**Operation** : updateDeviceInformation(householdId, deviceId, deviceInformation)

**Returns** : Updated Device Information.

**Preconditions** :

- User verification is successful.
- Device information is provided and valid.

**Postconditions** :

- The device information is updated.
- The device information is updated in the device library.

**Collaborators** :

- HouseholdService::verifyUser()
- DeviceLibraryService::updateDeviceInformation()

## Remove Device from Household {removedevicefromhousehold}

**Description** : Remove a device from a household.

**Operation** : removeDeviceFromHousehold(householdId, deviceId)

**Returns** : Confirmation that device information was removed.

**Preconditions** :

- User verification is successful.
- Device with the Id exists in the household.

**Postconditions** :

- The device is removed from the household.

**Collaborators** :

- HouseholdService::verifyUser()

## List Household Devices {listhouseholddevices}

**Description** : Retrieve a list of devices in a household.

**Operation** : listHouseholdDevices(householdId)

**Returns** : List of devices

**Preconditions** :

- User verification is successful.

**Postconditions** :

- A list of devices in the household is retrieved.

**Collaborators** :

- HouseholdService::verifyUser()

## Register Smart Meter Reader {registersmartmeterreader}

**Description** : Register a smart meter reader for a user.

**Operation** : registerSmartMeterReader(householdId, smartReaderId)

**Returns** : Success status

**Preconditions** :

- User verification is successful.

**Postconditions** :

- The smart meter reader is registered.

**Collaborators** :

- HouseholdService::verifyUser()
- SmartMeterService::addNewSmartMeter()

## Remove Smart Meter Reader {removesmartmeterreader}

**Description** : Remove a registered smart meter reader.

**Operation** : removeSmartMeterReader(householdId, smartMeterId)

**Returns** : Success status

**Preconditions** :

- User verification is successful.

**Postconditions** :

- The smart meter reader is removed.

**Collaborators** :

- HouseholdService::verifyUser()
- SmartMeterService::removeSmartMeter()

## Get Smart Meter Data {getsmartmeterdata}

**Description** : Retrieve smart meter data.

**Operation** : getSmartMeterData(householdId, smartMeterId)

**Returns** : Smart meter data

**Preconditions** :

- User verification is successful.

**Postconditions** :

- Smart meter data is retrieved.

**Collaborators** :

- HouseholdService::verifyUser()

## Create Researcher {createresearcher}

**Description** : Create a new researcher.

**Operation** : createResearcher(firstname, lastname, email)

**Returns** : Researcher ID

**Preconditions** :

- None

**Postconditions** :

- A new researcher was successfully created.

**Collaborators** :

## Create Project {createproject}

**Description** : Create a new project.

**Operation** : createProject(projektName, startDate, endDate)

**Returns** : Project ID

**Preconditions** :

- Researcher verification is successful.

**Postconditions** :

- A new project was successfully created.

**Collaborators** :

- ProjectService:verifyResearcher()

## Update Project Information {updateprojectinformation}

**Description** : Update project information.

**Operation** : updateProjectInformation(projektId, projektInformation)

**Returns** : Updated Projektinformation

**Preconditions** :

- Researcher verification is successful.
- Project information details are provided and valid.

**Postconditions** :

- Project information is updated.

**Collaborators** :

- ProjectService:verifyResearcher()
- ProjectService:verifyProjectInformationDetails()

## Update Project Settings {updateprojectsettings}

**Description** : Update project settings.

**Operation** : updateProjectSettings(projektId, projektSettings)

**Returns** : Success status

**Preconditions** :

- Researcher verification is successful.
- Project settings details are provided and valid.

**Postconditions** :

- Project settings are updated.

**Collaborators** :

- ProjectService:verifyResearcher()
- ProjectService:verifyProjectSettingsDetails()

## List Projects {listprojects}

**Description** : Retrieve a list of projects.

**Operation** : listProjects()

**Returns** : List of projects

**Preconditions** :

- User verification is successful.
- Researcher verification is successful.

**Postconditions** :

- A list of projects is retrieved.

**Collaborators** :

- HouseholdService::verifyUser()
- HouseholdService::verifyResearcher()

## Join Project {joinproject}

**Description** : Allow a user to join a project.

**Operation** : joinProject(projektId, householdId)

**Returns** : Success status

**Preconditions** :

- User verification is successful.
- User is part of the household.

**Postconditions** :

- The user has joined the project.

**Collaborators** :

- HouseholdService::verifyUser()

## Get Project Information {getprojectinformation}

**Description** : Retrieve project information.

**Operation** : getProjectInformation(projektId)

**Returns** : Project information

**Preconditions** :

- User verification is successful.

**Postconditions** :

- Project information is retrieved.

**Collaborators** :

- HouseholdService::verifyUser()

## Get Project Labels {getprojectlabels}

**Description** : Retrieve project labels.

**Operation** : getProjectLabels(projektId, householdId)

**Returns** : Project labels

**Preconditions** :

- User verification is successful.
- User is allowed to view labels.

**Postconditions** :

- Project labels are retrieved.

**Collaborators** :

- HouseholdService::verifyUser()

## Create Label {createlabel}

**Description** : Create a label.

**Operation** : createLabel(projektId, householdId, lableData)

**Returns** : Success status

**Preconditions** :

- User verification is successful.
- Lable information is provided and valid.

**Postconditions** :

- A labeled dataset is created.

**Collaborators** :

- HouseholdService::verifyUser()
- DeviceLibraryService::findDeviceInformation()

## List Devices {listdevices}

**Description** : List available devices.

**Operation** : listDevices()

**Returns** : List of devices

**Preconditions** :

- None

**Postconditions** :

- A list of available devices is retrieved.

**Collaborators** :
