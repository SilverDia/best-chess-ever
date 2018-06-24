#/bin/bash

earDir=$(ls -la | grep ear | awk '{print $9}')
mvn clean install

earFiles=( $(ls -la ${earDir}/target/ | grep '\.ear' | awk '{print $9}') )
for file in "${earFiles[@]}"
do
	echo "${file}"
	rm $WILDFLY_HOME/standalone/deployments/"${file}"*
	cp "${earDir}/target/${file}" "$WILDFLY_HOME/standalone/deployments/${file}"
done
