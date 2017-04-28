'use strict';

import React from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  TextInput,
  View
} from 'react-native';

class HelloWorld extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            oldPWD: '',
            newPWD: ''
        };
    }
  render() {
    return (
      <View style={{padding: 10}}>
              <TextInput
                style={{height: 40}}
                placeholder="Type old password!"
                onChangeText={(oldPWD) => this.setState({oldPWD})}
              />
              <TextInput
                style={{height: 40}}
                 placeholder="Type new password!"
                 onChangeText={(newPWD) => this.setState({newPWD})}
              />
      </View>
    );
  }
}
var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
  },
  hello: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
});

AppRegistry.registerComponent('HelloWorld', () => HelloWorld);