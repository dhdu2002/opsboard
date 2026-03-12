#!/usr/bin/env bash
set -euo pipefail

WAR_PATH="$1"
DEPLOY_HOST="$2"
DEPLOY_USER="$3"
DEPLOY_DIR="$4"
APP_NAME="$5"
TOMCAT_SERVICE="$6"

if [[ ! -f "$WAR_PATH" ]]; then
  echo "WAR file not found: $WAR_PATH"
  exit 1
fi

echo "[1/3] Uploading WAR to ${DEPLOY_USER}@${DEPLOY_HOST}:${DEPLOY_DIR}/${APP_NAME}.war"
scp "$WAR_PATH" "${DEPLOY_USER}@${DEPLOY_HOST}:${DEPLOY_DIR}/${APP_NAME}.war"

echo "[2/3] Restarting Tomcat service: ${TOMCAT_SERVICE}"
ssh "${DEPLOY_USER}@${DEPLOY_HOST}" "sudo systemctl restart ${TOMCAT_SERVICE}"

echo "[3/3] Deployment completed"
