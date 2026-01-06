# demo_docker_td
Demo microserver avec Docker (creation d'images)  
  
Dans le dossier ./demo_docker_td/ws_services_template  
```shell  
$> git clone https://github.com/univphf/demo_docker_td.git  
$> cd ./demo_docker_td  
$> chmod u+x compile.sh  
$> chmod u+x mwnw  
$> ./compile.sh  
$> cp ./target/ws_services_template-0.0.1.jar ./DockerHub/ws_services_template-0.0.1.jar 
$> cd DockerHub  
$> chmod u+x build_image.sh  
$> ./build_image.sh  
$> cd ../../pg-insa/  
$> chmod u+x build_image.sh  
$> ./build_image.sh  
$> cd ..
$> chmod u+c run.sh
$> chmod u+x stop.sh  
$> ./run.sh  
	# il ne reste plus qu'a tester.
``` 


