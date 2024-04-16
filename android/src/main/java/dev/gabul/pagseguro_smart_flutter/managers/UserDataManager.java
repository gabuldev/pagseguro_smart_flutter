package dev.gabul.pagseguro_smart_flutter.managers;



import dev.gabul.pagseguro_smart_flutter.user.UserData;
import dev.gabul.pagseguro_smart_flutter.user.usecase.DebitUserUseCase;
import dev.gabul.pagseguro_smart_flutter.user.usecase.EditUserUseCase;
import dev.gabul.pagseguro_smart_flutter.user.usecase.GetUserUseCase;
import dev.gabul.pagseguro_smart_flutter.user.usecase.NewUserUseCase;
import dev.gabul.pagseguro_smart_flutter.user.usecase.RefundUserUseCase;
import io.reactivex.Observable;
import io.reactivex.Single;

public class UserDataManager {

    private GetUserUseCase mGetUser;
    private NewUserUseCase mNewUser;
    private EditUserUseCase mEditUser;
    private RefundUserUseCase mRefundUser;
    private DebitUserUseCase mDebitUser;

    public UserDataManager(GetUserUseCase getUser, NewUserUseCase newUser, EditUserUseCase mEditUser, DebitUserUseCase mDebitUser, RefundUserUseCase mRefundUser) {
        this.mGetUser = getUser;
        this.mNewUser = newUser;
        this.mEditUser = mEditUser;
        this.mDebitUser = mDebitUser;
        this.mRefundUser = mRefundUser;
    }

    public Single<UserData> getUserData(String idEvento){
        return mGetUser.getUser(idEvento);
    }

    public Observable<Integer> writeUserData(UserData userData){
        return mNewUser.writeUserInNFcCard(userData);
    }

    public Observable<String> reWriteUserData(UserData userData){
        return mEditUser.reWriteUserInNFcCard(userData);
    }

    public Observable<String> reFundUserData(UserData userData){
        return mRefundUser.reFundUserInNFcCard(userData);
    }

    public Observable<String> debitUserData(UserData userData){
        return mDebitUser.debitInNFcCard(userData);
    }
}
