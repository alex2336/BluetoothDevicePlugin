package com.leotech.plugin;
    import lifesense.ble.bean.LSDeviceInfo;
    import org.apache.cordova.CordovaPlugin;
    import org.apache.cordova.CallbackContext;
    import lifesense.ble.commom.DeviceManagerCallback;
    import org.apache.cordova.PluginResult;
    import lifesense.ble.commom.BleDeviceManager;
    import org.apache.cordova.CordovaInterface;
    import org.apache.cordova.CordovaWebView;
    import org.json.JSONArray;
    import org.json.JSONException;
    import lifesense.ble.commom.DeviceType;
    import org.json.JSONObject;
    import java.util.ArrayList;
    /**
     * This class echoes a string called from JavaScript.
     */
    public class BluetoothDevicePlugin extends CordovaPlugin {
        private DeviceManagerCallback mDelegate;    
        private ArrayList<BleDevice> tempList=new ArrayList<BleDevice>();
        private BleDeviceManager bleDeviceManager;
        String ACTION_START_SCANNING = "startScanning";
        String GET_CURRENT_DEVICES = "getCurrentDevices";
        @Override
        public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
            if (action.equals(ACTION_START_SCANNING)) {
                startScanning();
                return true;
            }else if(action.equals(GET_CURRENT_DEVICES)){
                listKnownDevices(callbackContext);
                return true;
            }
            return false;
        }
        private void startScanning(){
            bleDeviceManager.setCallback(mDelegate);
            bleDeviceManager.startScanning();
        }
        private void listKnownDevices(CallbackContext callbackContext) {

            JSONObject json = new JSONObject();
            try{
                for(int i=0;i<tempList.size();i++){

                    json.put(tempList.get(i).getAddress(),tempList.get(i).getName());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            PluginResult result = new PluginResult(PluginResult.Status.OK, json);
            callbackContext.sendPluginResult(result);
        }
        @Override
        public void initialize(CordovaInterface cordova, CordovaWebView webView){
            super.initialize(cordova, webView);
            mDelegate=new DeviceManagerCallback(){
                @Override
                public void onDiscoverDevice(final LSDeviceInfo lsDevice) 
                {
                    if(lsDevice!=null&&!deviceExists(lsDevice.getDeviceName()))
                    {
                        BleDevice bleDevice=new BleDevice(
                                   lsDevice.getDeviceName(),
                                   lsDevice.getDeviceAddress(),
                                   lsDevice.getDeviceType(),
                                   lsDevice.getModelNumber());
                        tempList.add(bleDevice);    
                    }
                }
            };
            bleDeviceManager=BleDeviceManager.getInstance(); 
            bleDeviceManager.initialize(this.cordova.getActivity().getApplicationContext(),mDelegate);
        }
        private boolean deviceExists(String name) 
        {
            boolean found=false;
            for (int i = 0; i < tempList.size(); i++) 
            {
              if (tempList.get(i).getName().equals(name)) 
              {
                return found=true;
              }
            }
            return found;
        }
    }