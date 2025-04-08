package by.dima.model.data.command.model.impl;

import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.service.files.io.ScannerWrapper;
import lombok.Getter;
import lombok.Setter;

/**
 * Данная команда позволяет выйти из программы завершив поток выхода out для ввода с консоли. Перед
 * закрытием команды, выводится предупреждение о необходимости сохранить файл командой {@link SaveCommand}
 */
@Getter
@Setter
public class ExitCommand extends CommandAbstract {
    private final ScannerWrapper scannerWrapper;

    /**
     * Конструктор принимает обертку класса Scanner, которую можно закрыть в любом месте, куда передается эта ссылка
     * @param scannerWrapper
     */
    public ExitCommand(ScannerWrapper scannerWrapper) {
        super("exit", "Exit the program without or you can do saving to use save command.");
        this.scannerWrapper = scannerWrapper;
    }

    @Override
    public void execute() {
        System.err.println("If you want to save change you can use a save command!\nDo you want to continue exit without save? y/n ");
        String[] answer = scannerWrapper.newLineSplitSpace();
        if (answer.length == 1) {
            if (!answer[0].equals("y")) {
                System.err.println("Exit command rejected! Try again or save changes using save command!");
                return;
            }
        } else {
            System.err.println("Exit command rejected because you write invalid answer! Try again or save changes using save command!");
            return;
        }
        scannerWrapper.closeScanner();
    }
}
