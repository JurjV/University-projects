package repository;

import adt.structures.PrgState;
import controller.MyException;

import java.io.IOException;

public interface IRepository {
    PrgState getCrtPrg();
    void add(PrgState e);

    void logPrgStateExec() throws MyException, IOException;
}
