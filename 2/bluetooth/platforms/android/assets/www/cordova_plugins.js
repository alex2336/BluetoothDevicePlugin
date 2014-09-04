cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/org.apache.cordova.device/www/bluetoothDevicePlugin.js",
        "id": "org.apache.cordova.device.bluetoothDevicePlugin",
        "clobbers": [
            "bluetoothDevicePlugin"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "org.apache.cordova.device": "0.2.3"
}
// BOTTOM OF METADATA
});