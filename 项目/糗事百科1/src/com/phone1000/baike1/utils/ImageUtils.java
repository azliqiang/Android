package com.phone1000.baike1.utils;

import java.lang.ref.WeakReference;

import com.phone1000.baike1.R;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;


/**
 * 编写一个图片下载的工具类，为了快速浏览，做缓存处理
 * 第一步：下载功能
 * 第三步：软链接缓存
 * @author Administrator
 *
 */
public class ImageUtils {
	private LruCache<String, Bitmap> mMemoryCache=null;
	public ImageUtils(){
		final int maxMemory=(int) (Runtime.getRuntime().maxMemory()/1024);
		int cacheSize=maxMemory/8;
		mMemoryCache=new LruCache<String,Bitmap>(cacheSize){

			//If a cache miss should be computed on demand for the corresponding keys
			@Override
			protected Bitmap create(String key) {
				return super.create(key);
			}
			//If your cached values hold resources that need to be explicitly released
			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				super.entryRemoved(evicted, key, oldValue, newValue);
			}

			//By default, the cache size is measured in the number of entrie
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount()/1024;
			}

			@Override
			public void trimToSize(int arg0) {
				super.trimToSize(arg0);
			}
			
		};
	}
	public void loadBitmap(String url,ImageView imageView){
		final Bitmap bm=getBitmapFromMemCache(url);
		if(bm!=null){
			imageView.setImageBitmap(bm);
		}else{
			//从网上下载
			imageView.setImageResource(R.drawable.ic_launcher);
			imageView.setTag(url);//设置标志
			BitmapWorkerTask task=new BitmapWorkerTask(imageView);
			task.execute(url);
		}
	}
	private Bitmap getBitmapFromMemCache(String key){
		return mMemoryCache.get(key);
	}
	private void addBitmapToMemoryCache(String key,Bitmap value){
		if(getBitmapFromMemCache(key)==null){
			mMemoryCache.put(key, value);
		}
	}
	class BitmapWorkerTask  extends AsyncTask<String,Void,Bitmap>{
		private WeakReference<ImageView> imageViewReference;//使用若引用，好及时收回引用
		private String url=null;
		
		public BitmapWorkerTask(ImageView imageView){
			this.imageViewReference=new WeakReference<ImageView>(imageView);
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			this.url=params[0];
			System.out.println("url="+url);
			return new NetUtils().getImage(params[0]);
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			if(imageViewReference!=null && result!=null){
				ImageView temp=imageViewReference.get();
				if(temp!=null && temp.getTag()!=null && temp.getTag().equals(url)){
					temp.setImageBitmap(result);
				}
				addBitmapToMemoryCache(url,result);
			}
			
		}
	}
	
}
