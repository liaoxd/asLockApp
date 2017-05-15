'use strict';

import React from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  TextInput,
  View,
  TouchableHighlight,
  DeviceEventEmitter,
  NativeModules,
  ToastAndroid
} from 'react-native';

class HelloWorld extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            oldPWD: '',
            newPWD: ''
        };
    }
    _onPressButton() {
        console.log("You tapped the button!");
      }
    /**
     *
    */
    componentDidMout(){
        DeviceEventEmitter.addListener('nativeCallRN',(msg) =>{
            title = "React Native界面，接收到数据："+global.patchImgNames;
            ToastAndroid.show("发送成功",ToastAndroid.SHORT);
        })
    }
    /**
        * 调用原生代码
        */
        skipNativeCall() {
           let phone = '18380429225';
           NativeModules.TransMissionModule.rnCallNative(phone);
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
              <TouchableHighlight onPress={this.skipNativeCall.bind(this)}>
                      <Text>确定</Text>
              </TouchableHighlight>
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