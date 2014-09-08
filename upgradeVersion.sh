#   Licensed to the Apache Software Foundation (ASF) under one or more
#   contributor license agreements.  See the NOTICE file distributed with
#   this work for additional information regarding copyright ownership.
#   The ASF licenses this file to You under the Apache License, Version 2.0
#   (the "License"); you may not use this file except in compliance with
#   the License.  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.
#


oldVersion=$(grep "version" pom.xml | grep "1.8.1-CB" | grep -o -P "(?<=CB-).*(?=</version>)")
newVersion=$((oldVersion+1))

find . -name "pom.xml" -not -path "./archetypes/compute-service-archetype/target/*" -not -path "./archetypes/rest-client-archetype/target/*" | xargs sed -i "s/1.8.1-CB-$oldVersion/1.8.1-CB-$newVersion/"
find . -name "pom.xml" -not -path "./archetypes/compute-service-archetype/target/*" -not -path "./archetypes/rest-client-archetype/target/*" | xargs git add
git commit -m "Incrementing version number to 1.8.1-CB-$newVersion"