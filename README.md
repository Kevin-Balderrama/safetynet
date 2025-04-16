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

http://localhost:8080/childAlert?address=<address> 
This URL should return a list of children (anyone under the age of 18) at that address. The list should 
include the first and last name of each child, the child’s age, and a list of other persons living at that 
address. If there are no children at the address, then this URL can return an empty string. 
http://localhost:8080/childAlert?address=1509%20Culver%20St
http://localhost:8080/childAlert?address=947%20E.%20Rose%20Dr
http://localhost:8080/childAlert?address=644%20Gershwin%20Cir
<address> returns blank if no children present

http://localhost:8080/phoneAlert?firestation=<firestation_number> 
This URL should return a list of phone numbers of each person within the fire station’s jurisdiction.We’ll 
use this to send emergency text messages to specific households. 
http://localhost:8080/phoneAlert?firestation=1
http://localhost:8080/phoneAlert?firestation=4 
http://localhost:8080/phoneAlert?firestation=5 
<firestation_number> returns empty string if station does not exist

http://localhost:8080/fire?address=<address> 
This URL should return the fire station number that services the provided address as well as a list of all of 
the people living at the address. This list should include each person’s name, phone number, age, 
medications with dosage, and allergies. 
http://localhost:8080/fire?address=1509%20Culver%20St
http://localhost:8080/fire?address=947%20E.%20Rose%20Dr
http://localhost:8080/fire?address=644%20Gershwin%20Cir
<address> currently returns -1 as station_number regardless of actual, further correction needed

http://localhost:8080/flood/stations?stations=<a list of station_numbers> 
This should return a list of all the households in each fire station’s jurisdiction. This list needs to group 
people by household address, include name, phone number, and age of each person, and  any 
medications (with dosages) and allergies beside each person’s name.  
http://localhost:8080/flood/stations?stations=1
http://localhost:8080/flood/stations?stations=1,2
http://localhost:8080/flood/stations?stations=12
http://localhost:8080/flood/stations?stations=1,5
<a list of station_numbers> should be seperated by commas, returns empty list if non-existent

http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName> 
This should return the person’s name, address, age, email, list of medications with dosages and allergies. 
If there is more than one person with the same name, this URL should return all of them. 
http://localhost:8080/personInfo?firstName=John&lastName=Boyd
http://localhost:8080/personInfo?firstName=Tessa&lastName=Carman 
http://localhost:8080/personInfo?firstName=John&lastName=Carman
<firstName>, <lastName> must match person in database, returns empty array if person not found

http://localhost:8080/communityEmail?city=<city> 
This will return the email addresses of all of the people in the city.
http://localhost:8080/communityEmail?city=Culver
http://localhost:8080/communityEmail?city=Cooper
http://localhost:8080/communityEmail?city=New%20York
<city> returns empty array if city not in database