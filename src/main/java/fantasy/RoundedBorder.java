package main.java.fantasy;

import javax.swing.border.AbstractBorder;
import java.awt.*;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class RoundedBorder extends AbstractBorder {
    private final Color color;
    private final int thickness;
    private final int radius;

    public RoundedBorder(Color color, int thickness, int radius) {
        this.color = color;
        this.thickness = thickness;
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Ativar suavização
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Glow externo (sombra)
        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100)); // Transparente
        for (int i = 1; i <= 3; i++) {
            g2.setStroke(new BasicStroke(thickness + i * 2));
            g2.drawRoundRect(x + i, y + i, width - i * 2 - 1, height - i * 2 - 1, radius, radius);
        }

        // Borda principal
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRoundRect(x + thickness / 2, y + thickness / 2,
                width - thickness, height - thickness, radius, radius);

        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness + 3, thickness + 3, thickness + 3, thickness + 3);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(thickness + 3, thickness + 3, thickness + 3, thickness + 3);
        return insets;
    }
}


