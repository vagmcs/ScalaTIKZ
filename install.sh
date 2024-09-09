LATEST_VERSION=0.5.2
wget -q --show-progress -P /tmp https://github.com/vagmcs/ScalaTIKZ/releases/download/v"${LATEST_VERSION}"/ScalaTIKZ-"${LATEST_VERSION}".zip
unzip -oq -d /tmp /tmp/ScalaTIKZ-"${LATEST_VERSION}".zip && rm -r /tmp/ScalaTIKZ-"${LATEST_VERSION}".zip
mkdir -p "${HOME}"/.local/opt
cp -r /tmp/ScalaTIKZ-"${LATEST_VERSION}" "${HOME}"/.local/opt && rm -r /tmp/ScalaTIKZ-"${LATEST_VERSION}"
ln -sfn "${HOME}"/.local/opt/ScalaTIKZ-"${LATEST_VERSION}" "${HOME}"/.local/opt/scalatikz
echo "Add 'export PATH=${HOME}/.local/opt/scalatikz/bin:\${PATH}' to your $(basename "${SHELL}")"
