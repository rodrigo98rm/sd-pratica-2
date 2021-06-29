javac -cp compute.jar rmi/client/*.java 
java -cp .:compute.jar -Djava.rmi.server.codebase=file:compute.jar -Djava.security.policy=./rmi/client/client.policy rmi.client.ComputePi 192.168.0.102 45