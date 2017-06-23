package com.ssy.everything.util;

import android.content.Context;
import android.util.Log;

import com.ssy.everything.common.Config;
import com.tencent.bugly.crashreport.BuglyLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Log write to file include print log to android logcat
 */
public final class LogUtil {
    /**
     * Log level error
     */
    private static final int Log_Level_error = 4;

    /**
     * Log level warn
     */
    private static final int Log_Level_warn = 3;

    /**
     * Log level info
     */
    private static final int Log_Level_info = 2;

    /**
     * Log level debug
     */
    private static final int Log_Level_debug = 1;

    /**
     * Log level verbose
     */
    private static final int Log_Level_verbose = 0;

    /**
     * Log level state
     */
    private static int logLevel = 2;

    /**
     * write log to file, true: write , false no write
     */
    private static final boolean Log_IN_FILE = true;

    /**
     * Log WITH POSTION
     */
    private static final boolean Log_WITH_POSTION = true;

    /**
     * Log TAG
     */
    private static final String LOG_TAG = "Everything";

    /**
     * log File Name
     */
    private static String logFileName = null;

    /**
     * LogUtil Construct class
     */
    public LogUtil() {
    }

    /**
     * open Log function that write log to file
     *
     * @param context Context
     */
    public static void openLog(Context context) {
        initLogFileName();
        try {
            File pathFile = new File(Config.LOG_PATH);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
                Log.d(LOG_TAG, "mkdirs sucess:" + Config.LOG_PATH);
            } else {
                deleteOverdueFiles(Config.LOG_PATH);
            }
        } catch (Exception e) {
            LogUtil.e(LOG_TAG, "Check file path exception:" + e.toString());
        }
    }

    private static void initLogFileName() {
        Date CurDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(CurDate);
        logFileName = Config.LOG_PATH + "/everything-" + strDate + ".log";
    }

    /**
     * delete history log file that save 10 days.
     *
     * @param Path String
     */
    public static void deleteOverdueFiles(String Path) {
        try {
            long curDate = System.currentTimeMillis();
            File[] files = new File(Path).listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f.isFile()) {
                    long fileDate = f.lastModified();
                    // exceed 10 days
                    if ((curDate - fileDate) > 864000000) {
                        f.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * LogUtil currentTime
     */
    public static String currentTime() {
        String date = "";
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd hh:mm:ss");
            long currentTime = System.currentTimeMillis();
            if (currentTime > 0l) {
                date = sDateFormat.format(currentTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
            date = "";
        }
        return date;
    }

    /**
     * LogUtil verbose
     *
     * @param msg String
     */
    public static void v(String msg) {
        v(LOG_TAG, msg);
    }

    /**
     * LogUtil verbose
     *
     * @param tag String
     * @param msg String
     */
    public static void v(String tag, String msg) {
        try {
            if (Log_WITH_POSTION) {
                msg = "$ " + currentTime() + msg + " - " + new Throwable().getStackTrace()[1].toString();
            }
            Log.v(tag, msg);
            BuglyLog.v(tag,msg);
            if (Log_Level_verbose >= logLevel) {
                if (Log_IN_FILE) {
                    writeIntoFile(LOG_TAG + " v: " + msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * LogUtil debug
     *
     * @param msg String
     */
    public static void d(String msg) {
        d(LOG_TAG, msg);
    }

    /**
     * LogUtil debug
     *
     * @param tag String
     * @param msg String
     */
    public static void d(String tag, String info) {
        try {
            if (Log_WITH_POSTION) {
                info = "$ " + currentTime() + info + " - " + new Throwable().getStackTrace()[1].toString();
            }
            Log.d(tag, info);
            BuglyLog.d(tag,info);
            if (Log_Level_debug >= logLevel) {
                if (Log_IN_FILE) {
                    writeIntoFile(LOG_TAG + " d: " + info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * LogUtil info
     *
     * @param msg String
     */
    public static void i(String info) {
        i(LOG_TAG, info);
    }

    /**
     * LogUtil info
     *
     * @param tag String
     * @param msg String
     */
    public static void i(String tag, String info) {
        try {
            if (Log_WITH_POSTION) {
                info = "$ " + currentTime() + info + " - " + new Throwable().getStackTrace()[1].toString();
            }
            Log.i(tag, info);
            BuglyLog.i(tag,info);
            if (Log_Level_info >= logLevel) {
                if (Log_IN_FILE) {
                    writeIntoFile(LOG_TAG + " i: " + info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * LogUtil warn
     *
     * @param msg String
     */
    public static void w(String msg) {
        w(LOG_TAG, msg);
    }

    /**
     * LogUtil warn
     *
     * @param tag String
     * @param msg String
     */
    public static void w(String tag, String info) {
        try {
            if (Log_WITH_POSTION) {
                info = "$ " + currentTime() + info + " - " + new Throwable().getStackTrace()[1].toString();
            }
            Log.w(tag, info);
            BuglyLog.w(tag,info);
            if (Log_Level_warn >= logLevel) {
                if (Log_IN_FILE) {
                    writeIntoFile(LOG_TAG + " w: " + info);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * LogUtil error
     *
     * @param msg String
     */
    public static void e(String msg) {
        e(LOG_TAG, msg);
    }

    /**
     * LogUtil error
     *
     * @param tag String
     * @param msg String
     */
    public static void e(String tag, String info) {
        try {
            if (Log_WITH_POSTION) {
                info = "$ " + currentTime() + info + " - " + new Throwable().getStackTrace()[1].toString();
            }
            Log.e(tag, info);
            BuglyLog.e(tag,info);
            if (Log_Level_error >= logLevel) {
                if (Log_IN_FILE) {
                    writeIntoFile(LOG_TAG + " e: " + info);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * LogUtil error
     *
     * @param tag String
     * @param e   Exception
     */
    public static void e(String title, Exception e) {

        String msg = null;
        try {
            if (e == null) {
                msg = title + ": " + "null";
            } else {
                msg = title + ": " + e.toString();
            }
        } catch (Exception e2) {
            // TODO: handle exception
            e.printStackTrace();
            msg = null;
        }

        e(msg);
    }

    /**
     * LogUtil write log into file
     *
     * @param log String
     * @return boolean true:write flase no
     */
    public static boolean writeIntoFile(String log) {

        log = log + "# \n";

        boolean res = false;
        FileOutputStream outStream = null;
        try {
            if (null == logFileName || "".equals(logFileName)) {
                initLogFileName();
            }
            File logFile = new File(logFileName);
            if (!new File(Config.LOG_PATH).exists()) {
                return false;
            }
            if (logFile != null && !logFile.exists()) {
                logFile.createNewFile();
            }
            outStream = new FileOutputStream(logFile, true);
            outStream.write(log.getBytes());
            res = true;
        } catch (FileNotFoundException e) {
            LogUtil.e(LOG_TAG, e.toString());
        } catch (IOException e) {
            LogUtil.e(LOG_TAG, e.toString());
        } finally {
            try {
                if (null != outStream) {
                    outStream.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return res;
    }

    public static void warnDeprecation(String depreacted, String replacement) {
        Log.w(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
    }
}
