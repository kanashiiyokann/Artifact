package artifact.constant;


import artifact.annotation.Text;

public interface UserState {

    @Text("正常")
    Integer STATE_NORMAL=1;
    @Text("锁定")
    Integer STATE_LOCKED=2;
}
