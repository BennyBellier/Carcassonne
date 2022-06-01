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

  /**
   ** Détecte les clic sur le tableau des sauvegarde
   */
  @Override
  public void mouseClicked(MouseEvent me) {
    if (me.getClickCount() == 1) {
      JTable target = (JTable) me.getSource();
      int row = target.getSelectedRow();
      filename = (String) table.getValueAt(row, 0);
      frame.setSelectedSave(filename);
    }
    else if (me.getClickCount() == 2) {
      JTable target = (JTable) me.getSource();
      int row = target.getSelectedRow();
      filename = (String) table.getValueAt(row, 0);
      frame.selectedSaveActionPerformed(filename);
    }
  }
}
