package com.mg.weld;

import javax.enterprise.inject.Instance;

import org.apache.log4j.Logger;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.literal.AnyLiteral;

public class WeldManager {
	private static Logger logger = Logger.getLogger(WeldManager.class);

    private static WeldManager _instance = new WeldManager();

    private WeldContainer container;

    private WeldManager(){
        Weld weld = new Weld().property("org.jboss.weld.construction.relaxed", true);
        container = weld.initialize();
    }

    public static WeldManager getInstance(){
        return _instance;
    }

    public <T> Instance<T> select(Class<T> classOfT, String type) {
        return container.select(classOfT, new TypeLiteral(type));
    }

    public <T> T find(Class<T> classOfT, String type) {
        try {
            return select(classOfT, type).get();
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                System.out.println("*** WELD implementation not found: type='" + type + "'  class: " + classOfT);
                System.out.println("" + e.getMessage());
            }
            throw e;
        }
    }

    public <T> Instance<T> select(Class<T> classOfT) {
        return container.select(classOfT, AnyLiteral.INSTANCE);
    }
}
