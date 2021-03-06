*Please note that all watchtower components are under heavy development and the norm is that things will break. Please be patient with us until the first stable release.*

<div align="center">
	<img src="https://raw.githubusercontent.com/icclab/watchtower-common/master/watchtower.png" alt="Watchtower" title="Watchtower">
</div>

[![Build Status](https://travis-ci.org/icclab/watchtower-workflow.svg?branch=master)](https://travis-ci.org/icclab/watchtower-workflow)
[![Coverage Status](https://coveralls.io/repos/icclab/watchtower-workflow/badge.svg?branch=master)](https://coveralls.io/r/icclab/watchtower-workflow?branch=master)

# Overview

**watchtower-workflow** is part of **watchtower**. Its primary role is to provide an interface between the workflow engine and the rest of **watchtower**'s components.

## General Instructions

### Building

The best way to install **watchtower-workflow** is to download and build it with Maven. Please note you need to download and install **watchtower-common** beforehand.

```
git clone https://github.com/icclab/watchtower-workflow.git
cd watchtower-workflow
mvn clean package
```

### Installation

The easies way to install `watchtower-workflow` is to install the `deb` package:

```
sudo dpkg -i target/watchtower-workflow-{version}.deb
```

For those which want to manually do everything, they can use the generated `jar`.

### Configuration

`watchtower-workflow` comes with a sample configuration file which, after installation, is located in `/etc/watchtower/`. Best way is to work your way from the provided sample:

```
sudo cp /etc/watchtower/workflow-config.yml-sample /etc/watchtower/workflow-config.yml
```

### Running

The `deb` file contains service files for `Upstart` so you can just do:

```
sudo service watchtower-workflow start
```

Please note that the service does not automatically start after `deb` installation.


# License

Copyright 2015 Zurich University of Applied Sciences

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
    
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
implied.
See the License for the specific language governing permissions and
limitations under the License.

# Author Information

For further information or assistance please contact [**Victor Ion Munteanu**](https://github.com/nemros).