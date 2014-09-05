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
import android.util.Log;
	/**
	 * This class echoes a string called from JavaScript.
	 */
	public class LifesenseBLEPlugin extends CordovaPlugin {
		private DeviceManagerCallback mDelegate;    
		private LSDeviceInfo mDevice;
		private LSDeviceInfo pairedDevice;
		private ArrayList<BleDevice> tempList=new ArrayList<BleDevice>();
		private ArrayList<BleDevice> deviceList=new ArrayList<BleDevice>();
		private BleDeviceManager bleDeviceManager;
		private TypeConversion typeConversion;
		String ACTION_START_SCANNING = "startScanning";
		String ACTION_STOP_SCANNING = "stopScanning";
		String GET_CURRENT_DEVICES = "getCurrentDevices";
		String PAIR_DEVICE = "pairDevice";
		String GET_PARIED_DEVICE = "getPairedDevice";
		@Override
		public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
			if (action.equals(ACTION_START_SCANNING)) {
				Log.d("CordovaLog:","start scanning!");
				startScanning();
				return true;
			}else if(action.equals(ACTION_STOP_SCANNING)){
				stopScanning();
				return true;
			}else if(action.equals(GET_CURRENT_DEVICES)){
				listKnownDevices(callbackContext);
				return true;
			}else if(action.equals(PAIR_DEVICE)){
				pairDevice(args.getString(0),args.getInt(1),args.getString(2),args.getString(3));
				return true;
			}else if(action.equals(GET_CURRENT_DEVICES)){
				getPairedDevice(callbackContext);
				return true;
			}
			return false;
		}
		private void startScanning(){
			bleDeviceManager.setCallback(mDelegate);
			bleDeviceManager.startScanning();
		}
		private void stopScanning(){
			bleDeviceManager.stopScanning();
		}
		private void pairDevice(String name, int type, String address, String modelNumber){
			mDevice=new LSDeviceInfo();
			mDevice.setDeviceName(name);
			mDevice.setDeviceType(typeConversion.integerToEnum(type));
			mDevice.setDeviceAddress(address);
			mDevice.setModelNumber(modelNumber);
			stopScanning();
			mDelegate=new DeviceManagerCallback(){
				@Override
				public void onPairedResults(final LSDeviceInfo device, final int state) {
					if(device!=null&&state==0)
					{
						pairedDevice = device;
					}
				}
			};
			bleDeviceManager.toPairDevice(mDevice);
		}
		private void sendErrorMessage(CallbackContext callbackContext, String errorMessage) {
			JSONObject json = new JSONObject();
			try{
				json.put("error",errorMessage);
			}catch(Exception e){
				e.printStackTrace();
			}
			PluginResult result = new PluginResult(PluginResult.Status.OK, json);
			callbackContext.sendPluginResult(result);
		}
		private void listKnownDevices(CallbackContext callbackContext) {

			JSONArray json = new JSONArray();
			try{
				for(int i=0;i<tempList.size();i++){
					JSONObject obj = new JSONObject();
					obj.put(tempList.get(i).getAddress(),tempList.get(i).getName());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			PluginResult result = new PluginResult(PluginResult.Status.OK, json);
			callbackContext.sendPluginResult(result);
		}
		private void getPairedDevice(CallbackContext callbackContext) {

			JSONObject json = new JSONObject();
			try{
				if(pairedDevice!=null){
					json.put(pairedDevice.getDeviceAddress(),pairedDevice.getBroadcastID());
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
			typeConversion=new TypeConversion();
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