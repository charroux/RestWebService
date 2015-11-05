# RestWebService

proxy configuration :

  export http_proxy=http://proxy.efrei.fr:3128
  
  export https_proxy=$http_proxy
  
  export ftp_proxy=$http_proxy
  
  export rsync_proxy=$http_proxy
  
  export no_proxy="localhost,127.0.0.1"

Eviter d'avoir à saisir yes (y) lors d'une installation :

  yes | command
  
  par exemple yes | yum install unzip
  
  pas utile pour wget ...

Installation Java :

  sudo yum install java-1.7.0-openjdk-devel

Installation wget :

  yum install wget

Installation et lancement database :

  mkdir database
  
  cd database
  
  wget http://sourceforge.net/projects/hsqldb/files/hsqldb/hsqldb_1_8_1/hsqldb_1_8_1_3.zip
  
  yum install unzip
  
  unzip hsqldb_1_8_1_3.zip
  
  nohup java -cp /root/database/hsqldb/lib/hsqldb.jar org.hsqldb.Server &
  
  cd ..

Installation Gradle :

  mkdir gradle
  
  cd gradle
  
  wget https://services.gradle.org/distributions/gradle-2.8-bin.zip
  
  unzip gradle-2.8-bin.zip
  
  cd ..

Configuratoon Gradle :

  vi .bashrc
  
  ajouter à la fin la commande :
  
  export PATH=$PATH:/root/gradle/gradle-2.8/bin

Utilisation des commandes vi :

  o 	ouvre une nouvelle ligne en dessous de la ligne courante
  
  i 	insère du texte avant le curseur
  
  ESC pour quitter le mode INSERT
  
  :w 	enregistre le fichier courant
  
  :q 	quitte vi

Installation ActiveMQ :

  wget wwwftp.ciril.fr/pub/apache/activemq/5.12.1/apache-activemq-5.12.1-bin.tar.gz
  
  tar zxvf apache-activemq-5.12.1-bin.tar.gz
  
  nohup ./activemq start & dans le dossier bin d'activeMQ
  
  ./activemq stop pour arrêter activeMQ
  
Installation MongoDB :

  create a /etc/yum.repos.d/mongodb-org-2.6.repo file
  
  [mongodb-org-2.6]
  
    name=MongoDB 2.6 Repository
  
    baseurl=http://downloads-distro.mongodb.org/repo/redhat/os/x86_64/
  
    gpgcheck=0
  
    enabled=1
  
  yum install -y mongodb-org
  
  Set SELinux to permissive mode in /etc/selinux/config by changing the SELINUX setting to permissive .
  
    SELINUX=permissive
    
  service mongod start
  
  You can verify that the mongod process has started successfully by checking the contents of the log file at /var/log/mongodb/mongod.log for a line reading
  
  [initandlisten] waiting for connections on port <port>
  
  service mongod stop

Installation Apache Tomcat :

  mkdir tomcat
  
  cd tomcat
  
  wget http://mirror.nl.webzilla.com/apache/tomcat/tomcat-7/v7.0.65/bin/apache-tomcat-7.0.65.tar.gz
  
  tar xzf apache-tomcat-7.0.65.tar.gz

  cd ..

  démarrer apache tomcat : nohup ./startup.sh dans le bin d'apache tomcat
  
  stopper apache tomcat ./shutdown.sh
  
Installation git :

  yum install git

Téléchargement et déploiement projet :

  mkdir RentCarService
  
  cd RentCarService
  
  mkdir RestWebService
  
  cd RestWebService
  
  git init
  
  git pull http://github.com/charroux/RestWebService

  déployer avec Gradle : gradle cargoDeployRemote


Eteindre la machine : 

  shutdown
