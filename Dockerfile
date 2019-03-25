# Copyright (c) 2019-present Sonatype, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

FROM groovy:2.5-jre-alpine

ENV SONATYPE_DIR=/opt/sonatype

COPY src/main/groovy/NexusPublisher.groovy ${SONATYPE_DIR}/bin/NexusPublisher.groovy
COPY target/nexus-platform-cli.jar ${SONATYPE_DIR}/bin/nexus-platform-cli.jar
COPY src/main/sh/evaluate.sh ${SONATYPE_DIR}/bin/evaluate.sh
COPY src/main/sh/publish.sh ${SONATYPE_DIR}/bin/publish.sh

# Run script once to bake Grab dependencies into image
RUN groovy ${SONATYPE_DIR}/bin/NexusPublisher.groovy || true
