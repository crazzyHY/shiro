package cn.craz.shiro.session;

public class ShiroSession extends AbstractSession {
	public ShiroSession() {
		super();
		this.setChanged(true);
	}

	public ShiroSession(String host) {
		super(host);
		this.setChanged(true);
	}
}
