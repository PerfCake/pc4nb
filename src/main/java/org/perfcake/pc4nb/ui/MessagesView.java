package org.perfcake.pc4nb.ui;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.pc4nb.core.model.MessageModel;
import org.perfcake.pc4nb.core.model.MessagesModel;
import org.perfcake.pc4nb.ui.actions.AddMessageAction;

/**
 *
 * @author Andrej Halaj
 */
public class MessagesView extends TopLevelView {

    private JMenuItem addComponent = new JMenuItem("Add new message");
    private JPopupMenu menu = new JPopupMenu();
    private TransferHandler transferHandler = new MessagesView.MessageTransferHandler();

    public MessagesView() {
        super("Messages");
        addComponent.addActionListener(new AddMessageAction(this));
        menu.add(addComponent);
        this.setComponentPopupMenu(menu);
        this.setTransferHandler(transferHandler);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof MessagesModel) {
            MessagesModel model = (MessagesModel) evt.getSource();

            this.removeAll();

            for (Scenario.Messages.Message message : model.getMessages().getMessage()) {
                this.add(new SecondLevelView(message.getContent()));
            }
        }
    }

    private final class MessageTransferHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(MessageModel.DATA_FLAVOR);
        }

        @Override
        public boolean importData(TransferSupport support) {
            try {
                MessageModel model = (MessageModel) support.getTransferable().getTransferData(MessageModel.DATA_FLAVOR);

                //MessageController messageController = new MessageController(new MessageModel(new Message()), new MessageWizardPanel());
                return true;
            } catch (UnsupportedFlavorException | IOException ex) {
                return false;
            }
        }
    }
}
