package ar.com.corpico.appcorpico.operarios.presentation;

import java.util.List;

import ar.com.corpico.appcorpico.operarios.domain.entity.Operario;

public interface View {
    void setPresenter(OperariosPresenter operarioPresenter);
    void showOperarioList(List<Operario> operario);
    void showOperarioError(String error);
    void showOperarioEmpty();
    void showProgressIndicator(boolean show);
    void close();
}
