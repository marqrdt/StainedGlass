package org.marqrdt.stainedglass;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

public class BorderDecorator extends JComponent {

	protected JComponent child;

	public BorderDecorator(JComponent component) {
		child = component;
		this.setLayout(new BorderLayout());
		this.add(child);
		this.setBorder(new LineBorder(Color.BLACK));
	}
}
