 1049  mkdir springboot
 1050  mv * springboot
 1051  ls
 1052  ls -l
 1053  cd springboot
 1054  ./mvnw install
 1055  ls -l
 1056  ./mvnw spring-boot:build-image
 1057  ls -l
 1058  more HELP.md
 1059  more pom.xml
 1060  kubectl create deployment demo --image=springguides/demo --dry-run -o=yaml > deployment.yaml
 1061  cat deployment.yaml
 1062  echo --- >> deployment.yaml
 1063  kubectl create service clusterip demo --tcp=8080:8080 --dry-run -o=yaml >> deployment.yaml
 1064  history
