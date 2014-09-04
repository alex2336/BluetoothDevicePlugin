var bluetoothDevicePlugin = {
    startScanning: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'BluetoothDevicePlugin', // mapped to our native Java class called "CalendarPlugin"
            'startScanning', // with this action name
            [{test:"test"}]
        ); 
     }
}
module.exports = bluetoothDevicePlugin;