javac -cp compute.jar rmi/engine/ComputeEngine.java
java -cp .:compute.jar -Djava.rmi.server.codebase=file:/. -Djava.rmi.server.hostname=192.168.0.102 -Djava.security.policy=./rmi/engine/server.policy rmi.engine.ComputeEngine