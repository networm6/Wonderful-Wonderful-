package android.kz.BaseBean;
import android.os.Build;
import java.util.List;
import android.graphics.Rect;
import java.util.ArrayList;
import android.support.annotation.RequiresApi;
import android.support.annotation.Nullable;
import android.kz.Bangscreentools.BangScreenSupport;
import android.kz.Bangscreentools.PhoneManufacturersJudgeTools;
import android.view.Window;
import android.kz.Bangscreentools.PBangScreen;
import android.kz.Bangscreentools.OppoBangScreen;
import android.kz.Bangscreentools.VivoBangScreen;
import android.kz.Bangscreentools.HuaWeiBangScreen;
import android.content.Context;
import android.kz.Bangscreentools.MiuiBangScreen;

final class SinkFullScreen
{
	public static final SinkFullScreen INSTANCE = new SinkFullScreen();
	private static final BangScreenSupport support;


	static {
		int i = Build.VERSION.SDK_INT;
		if (i < 26) {
			support = (BangScreenSupport) new EmptyScreenSupport();
		} else if (i < 28) {
			PhoneManufacturersJudgeTools pMJTools = PhoneManufacturersJudgeTools.getPMJTools();
			;
			if (pMJTools.isHuaWei()) {
				support = (BangScreenSupport) new HuaWeiBangScreen();
			} else if (pMJTools.isMiui()) {
				support = (BangScreenSupport) new MiUi();
			} else if (pMJTools.isVivo()) {
				support = (BangScreenSupport) new VivoBangScreen();
			} else if (pMJTools.isOppo()) {
				support = (BangScreenSupport) new OppoBangScreen();
			} else {
				support = (BangScreenSupport) new EmptyScreenSupport();
			}
		} else {
			support = (BangScreenSupport) new PBangScreen();
		}
	}

	private SinkFullScreen() {
	}

	public final void extendStatusCutout( Window window, Context context) {
		support.extendStatusCutout(window, context);
	}

	public final void blockStatusCutout(Window window) {

		support.setWindowLayoutBlockNotch(window);
	}

	public final boolean hasStatusCutout( Window window) {

		return support.hasNotBangScreen(window);
	}


}
class MiUi extends MiuiBangScreen{
	MiUi() {
	}

	@RequiresApi(26)
	public void extendStatusCutout(@Nullable Window window, @Nullable Context context) {
		if (window != null) {
			int statusBarColor = window.getStatusBarColor();
			super.extendStatusCutout(window, context);
			window.setStatusBarColor(statusBarColor);
		}
	}
}

class EmptyScreenSupport implements BangScreenSupport
{
	public void extendStatusCutout(Window window, Context context)
	{

	}

	public void fullscreen(Window window, Context context)
	{

	}

	public boolean hasNotBangScreen(Window window)
	{

		return false;
	}

	public void setWindowLayoutBlockNotch(Window window)
	{

	}

	public void transparentStatusCutout(Window window, Context context)
	{

	}

	public List<Rect> getBangSize(Window window)
	{
		return (List) new ArrayList();
	}
}
