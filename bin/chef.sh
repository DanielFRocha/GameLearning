#!/bin/bash

[ -z ${1} ] && echo "[NOK] : You need to specify the environment. ENV=[prod|uat|qa|dev]" && exit 1
[ -z ${2} ] && echo "[NOK] : You need to specify the environment. ROLE=[base|magento-consumer|magento-website|rabbitmq]" && exit 1

NAME=`hostname -s`
IP=`hostname -I`
FQDN="${IP} ${NAME}.abi-b2b-dc.net ${NAME}"

grep -E "${NAME}|${IP}" /etc/hosts
if [ $? -eq 0 ]; then
  echo "[OK] /etc/hosts"
elif [ $? -eq 1 ]; then
  echo ${FQDN} >> /etc/hosts
  sudo hostnamectl set-hostname ${NAME}.abi-b2b-dc.net --static
  sudo hostnamectl set-hostname ${NAME}.abi-b2b-dc.net --pretty
  sudo hostnamectl set-hostname ${NAME}.abi-b2b-dc.net --transient
fi

hostname -f | grep abi-b2b-dc.net
if [ $? -eq 1 ]; then
  echo "Please, fix your hostname!" && exit 1
fi

# Install Chef
sudo mkdir -p /var/log/chef/
sudo curl -L https://www.chef.io/chef/install.sh | bash -s -- -v 13.8.5
sudo mkdir /etc/chef
sudo rm -f /etc/chef/client.*

# Create client.rb
cat <<EOF > /etc/chef/client.rb
chef_server_url "https://chef-server.abi-b2b-dc.net/organizations/abi-b2b"
client_fork true
log_location "/var/log/chef/client.log"
ssl_verify_mode :verify_peer
validation_client_name "abi-b2b-validator"
verify_api_cert true
# Using default node name (fqdn)

# Do not crash if a handler is missing / not installed yet
begin
rescue NameError => e
  Chef::Log.error e
end
EOF

# Create validation.pem
sudo wget -q https://chef-server.abi-b2b-dc.net/shared/abi-b2b-validator.pem -O /etc/chef/validation.pem
sudo chmod 600 /etc/chef/validation.pem

# Add Role on host
sudo echo '{"run_list":["role['"${2}"']"]}' > /etc/chef/first-boot.json
sudo chef-client -E ${1} -j /etc/chef/first-boot.json
