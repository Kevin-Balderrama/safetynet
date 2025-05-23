# SafetyNet Design Document

## 1. Introduction
- **Purpose**: Provide emergency information to first responders, such as fire stations and medical records, to enhance emergency response efficiency.
- **Scope**: Backend services implemented in Java with a small HTML component for UI. The system interacts with fire stations, medical records, and personal information.

## 2. System Overview
- **Architecture**: Service-oriented architecture with RESTful APIs.
- **Key Features**:
  - Retrieve fire station and resident information.
  - Manage medical records and fire station assignments.
  - Provide emergency contact details.

## 3. Functional Specifications
- **Endpoints**:
  - `GET /firestation`: Returns residents by fire station number.
  - `GET /childAlert`: Lists children at a given address.
  - `POST /medicalRecord`: Add medical records.
  - `PUT /medicalRecord`: Update medical records.
  - `DELETE /medicalRecord`: Delete medical records.

http://localhost:8080/firestation?stationNumber=<station_number> 

This URL should return a list of people serviced by the corresponding fire station. So if station number = 1, 
it should return the people serviced by station number 1. The list of people should include these specific 
pieces of information: first name, last name, address, phone number. As well, it should provide a 
summary of the number of adults in the service area and the number of children (anyone aged 18 or 
younger). 

http://localhost:8080/firestation?stationNumber=1

http://localhost:8080/firestation?stationNumber=2

http://localhost:8080/firestation?stationNumber=5

<station_number> returns blank if station does not exist

```mermaid
sequenceDiagram
    participant User
    participant Controller as SafetyNetController
    participant Service as SafetyNetService
    participant Repository as LoadJson
    participant Data as data.json

    User->>Controller: GET /firestation?stationNumber=1
    Controller->>Service: getPersonsByStation(1)
    Service->>Repository: getFireStations()
    Repository->>Data: Read firestations
    Repository-->>Service: List<FireStation>
    Service->>Repository: getPersons()
    Repository->>Data: Read persons
    Repository-->>Service: List<Person>
    Service-->>Controller: Map (persons, adults, children)
    Controller-->>User: JSON response
```

http://localhost:8080/childAlert?address=<address> 

This URL should return a list of children (anyone under the age of 18) at that address. The list should 
include the first and last name of each child, the child’s age, and a list of other persons living at that 
address. If there are no children at the address, then this URL can return an empty string. 

http://localhost:8080/childAlert?address=1509%20Culver%20St

http://localhost:8080/childAlert?address=947%20E.%20Rose%20Dr

http://localhost:8080/childAlert?address=644%20Gershwin%20Cir

<address> returns blank if no children present

```mermaid
sequenceDiagram
    participant User
    participant Controller as SafetyNetController
    participant Service as SafetyNetService
    participant Repository as LoadJson
    participant Data as data.json

    User->>Controller: GET /childAlert?address=1509 Culver St
    Controller->>Service: getChildrenByAddress(address)
    Service->>Repository: getPersons()
    Repository->>Data: Read persons
    Repository-->>Service: List<Person>
    Service-->>Controller: List<Child> + List<OtherResidents>
    Controller-->>User: JSON response
```

http://localhost:8080/phoneAlert?firestation=<firestation_number> 

This URL should return a list of phone numbers of each person within the fire station’s jurisdiction.We’ll 
use this to send emergency text messages to specific households. 

http://localhost:8080/phoneAlert?firestation=1

http://localhost:8080/phoneAlert?firestation=4 

http://localhost:8080/phoneAlert?firestation=5 

<firestation_number> returns empty string if station does not exist

```mermaid
sequenceDiagram
    participant User
    participant Controller as SafetyNetController
    participant Service as SafetyNetService
    participant Repository as LoadJson
    participant Data as data.json

    User->>Controller: GET /phoneAlert?firestation=1
    Controller->>Service: getPhoneNumbersByStation(stationNumber)
    Service->>Repository: getFireStations()
    Repository->>Data: Read firestations
    Repository-->>Service: List<FireStation>
    Service->>Repository: getPersons()
    Repository->>Data: Read persons
    Repository-->>Service: List<Person>
    Service-->>Controller: List<PhoneNumbers>
    Controller-->>User: JSON response
```

http://localhost:8080/fire?address=<address> 

This URL should return the fire station number that services the provided address as well as a list of all of 
the people living at the address. This list should include each person’s name, phone number, age, 
medications with dosage, and allergies. 

http://localhost:8080/fire?address=1509%20Culver%20St

http://localhost:8080/fire?address=947%20E.%20Rose%20Dr

http://localhost:8080/fire?address=644%20Gershwin%20Cir

<address> currently returns -1 as station_number regardless of actual, further correction needed

```mermaid
sequenceDiagram
    participant User
    participant Controller as SafetyNetController
    participant Service as SafetyNetService
    participant Repository as LoadJson
    participant Data as data.json

    User->>Controller: GET /fire?address=1509 Culver St
    Controller->>Service: getFireData(address)
    Service->>Repository: getFireStations()
    Repository->>Data: Read firestations
    Repository-->>Service: List<FireStation>
    Service->>Repository: getPersons()
    Repository->>Data: Read persons
    Repository-->>Service: List<Person>
    Service->>Repository: getMedicalRecords()
    Repository->>Data: Read medical records
    Repository-->>Service: List<MedicalRecord>
    Service-->>Controller: stationNumber + List<PersonDetails>
    Controller-->>User: JSON response
```

http://localhost:8080/flood/stations?stations=<List> 

This should return a list of all the households in each fire station’s jurisdiction. This list needs to group 
people by household address, include name, phone number, and age of each person, and  any 
medications (with dosages) and allergies beside each person’s name.  

http://localhost:8080/flood/stations?stations=1

http://localhost:8080/flood/stations?stations=1,2

http://localhost:8080/flood/stations?stations=12

http://localhost:8080/flood/stations?stations=1,5

<list of station_numbers> should be seperated by commas, returns empty list if non-existent

```mermaid
sequenceDiagram
    participant User
    participant Controller as SafetyNetController
    participant Service as SafetyNetService
    participant Repository as LoadJson
    participant Data as data.json

    User->>Controller: GET /flood/stations?stations=1,2
    Controller->>Service: getHouseholdsByStations([1,2])
    Service->>Repository: getFireStations()
    Repository->>Data: Read firestations
    Repository-->>Service: List<FireStation>
    Service->>Repository: getPersons()
    Repository->>Data: Read persons
    Repository-->>Service: List<Person>
    Service->>Repository: getMedicalRecords()
    Repository->>Data: Read medical records
    Repository-->>Service: List<MedicalRecord>
    Service-->>Controller: Map<Address, List<PersonDetails>>
    Controller-->>User: JSON response
```

http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName> 

This should return the person’s name, address, age, email, list of medications with dosages and allergies. 
If there is more than one person with the same name, this URL should return all of them. 

http://localhost:8080/personInfo?firstName=John&lastName=Boyd

http://localhost:8080/personInfo?firstName=Tessa&lastName=Carman 

http://localhost:8080/personInfo?firstName=John&lastName=Carman

<firstName>, <lastName> must match person in database, returns empty array if person not found

```mermaid
sequenceDiagram
    participant User
    participant Controller as SafetyNetController
    participant Service as SafetyNetService
    participant Repository as LoadJson
    participant Data as data.json

    User->>Controller: GET /personInfo?firstName=John&lastName=Boyd
    Controller->>Service: getPersonInfo(firstName, lastName)
    Service->>Repository: getPersons()
    Repository->>Data: Read persons
    Repository-->>Service: List<Person>
    Service->>Repository: getMedicalRecords()
    Repository->>Data: Read medical records
    Repository-->>Service: List<MedicalRecord>
    Service-->>Controller: List<PersonInfo>
    Controller-->>User: JSON response
```

http://localhost:8080/communityEmail?city=<city> 

This will return the email addresses of all of the people in the city.

http://localhost:8080/communityEmail?city=Culver

http://localhost:8080/communityEmail?city=Cooper

http://localhost:8080/communityEmail?city=New%20York

<city> returns empty array if city not in database

```mermaid
sequenceDiagram
    participant User
    participant Controller as SafetyNetController
    participant Service as SafetyNetService
    participant Repository as LoadJson
    participant Data as data.json

    User->>Controller: GET /communityEmail?city=Culver
    Controller->>Service: getEmailsByCity(city)
    Service->>Repository: getPersons()
    Repository->>Data: Read persons
    Repository-->>Service: List<Person>
    Service-->>Controller: List<Email>
    Controller-->>User: JSON response
```

- **Data Models**:
  - `Person`: Represents an individual with attributes like name, address, and medical record.
  - `FireStation`: Maps addresses to fire station numbers.
  - `MedicalRecord`: Stores medical history, medications, and allergies.

```mermaid
classDiagram
    class FireStation {
        String station
        Set addresses
    }
    class Person {
        String firstName
        String lastName
        String address
        String city
        String zip
        String phone
        String email
        int age
    }
    class MedicalRecord {
        String firstName
        String lastName
        String birthdate
        List medications
        List allergies
    }

    FireStation "1" -- "*" Person : covers
    Person "1" -- "1" MedicalRecord : has
```

## 4. Non-Functional Requirements
- **Performance**: Handle requests for large datasets efficiently.
- **Reliability**: Ensure data consistency and accurate emergency details.
- **Security**: Protect sensitive personal and medical information with encryption and authentication.

## 5. System Design
- **Modules**:
  - `Controller`: Handles API requests and maps them to services.
  - `Service`: Implements business logic for handling data.
  - `Repository`: Manages data storage and retrieval.
- **Data Flow**:
  - API Request → Controller → Service → Repository → Response

## 6. Database Design
- **Tables**:
  - `Persons`: Stores personal information.
  - `FireStations`: Maps addresses to station numbers.
  - `MedicalRecords`: Stores medical history and allergies.
- **Relationships**:
  - One-to-Many: FireStations ↔ Addresses
  - One-to-One: Persons ↔ MedicalRecords

## 7. UI Design
- **HTML Component**: Displays a welcome message and project purpose.
- **Future Enhancements**:
  - Interactive dashboard for emergency responders.

## 8. Future Enhancements
- Add real-time notification features.
- Integrate geographic information systems (GIS) for mapping.
- Support for multi-language localization.

## 9. Testing
- **Methodology**:
  - Unit Tests: Validate individual modules.
  - Integration Tests: Verify data flow between modules.
- **Tools**:
  - JUnit for automated testing.
  - Mock data for simulating real-world scenarios.