package org.shest;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.JSObject;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrowserAction implements ActionListener {

    private JComponent component;
    private String url;
    private int offset;

    private static final String SCRIPT = "window.onload = function() {"
            + "const element = document.querySelector('#cross_rate_markets_stocks_1');\n"
            + "const observer = new MutationObserver(\n"
            + "    function(mutations) {\n"
            + "        window.java.onDomChanged(element.innerText);\n"
            + "    });\n"
            + "const config = {childList: true, subtree: true};\n"
            + "observer.observe(element, config); };";

    public BrowserAction(JComponent component, String url, int offset) {
        this.component = component;
        this.offset = offset;
        this.url = url;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        component.add(createInternalFrame("Monitoring", url, offset));
    }

    private JInternalFrame createInternalFrame(String title, String url, int offset) {
        Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
        BrowserView view = new BrowserView(browser);

        browser.addScriptContextListener(new ScriptContextAdapter() {
            @Override
            public void onScriptContextCreated(ScriptContextEvent event) {
                Browser browser = event.getBrowser();
                JSObject window = browser.executeJavaScriptAndReturnValue("window")
                        .asObject();
                window.setProperty("java", new JavaObject());
                browser.executeJavaScript(SCRIPT);
            }
        });

        browser.loadURL(url);
        JInternalFrame internalFrame = new JInternalFrame(title, true);
        internalFrame.setContentPane(view);
        internalFrame.setLocation(0, offset);
        internalFrame.setSize(1000, 1000 - offset);
        internalFrame.setVisible(true);
        return internalFrame;
    }

    /**
     * The object observing DOM changes.
     * <p>
     * <p>The class and methods that are invoked from JavaScript code must be public.
     */
    public static class JavaObject {

        @SuppressWarnings("unused") // invoked by callback processing code.
        public void onDomChanged(String innerHtml) {
            System.out.println(innerHtml);
        }
    }
}



