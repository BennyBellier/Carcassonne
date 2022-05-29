package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

public class JTableMouse extends MouseAdapter {
  String filename;
  JTable table;
  Frames frame;

  public JTableMouse(JTable table, Frames frame) {
    this.table = table;
    this.frame = frame;
  }

  @Override
  public void mouseClicked(MouseEvent me) {
    if (me.getClickCount() == 2) {
      JTable target = (JTable) me.getSource();
      int row = target.getSelectedRow();
      filename = (String) table.getValueAt(row, 0);
      frame.selectedSaveActionPerformed(filename);
    }
  }
}
