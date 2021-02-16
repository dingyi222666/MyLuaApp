package com.dingyi.MyLuaApp.core.task;

import android.os.AsyncTask;

public class SimpleAsyncTask {

    public static void postTask(Runnable run,Runnable callback) {
        new BaseAsyncTask()
                .setAsyncTask(run)
                .setCallback(callback)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
    }

    static class BaseAsyncTask extends AsyncTask<String,String,String> {

        private Runnable runCode;

        private Runnable callback;

        @Override
        protected String doInBackground(String... strings) {
            runCode.run();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            callback.run();
            super.onPostExecute(s);
        }

        public BaseAsyncTask setAsyncTask(Runnable run) {
            runCode=run;
            return this;
        }

        public BaseAsyncTask setCallback(Runnable callback) {
            this.callback = callback;
            return this;
        }
    }

}
