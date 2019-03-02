package artifact.modules.user.constant;

import artifact.common.annotation.Text;

public interface UserState {

    @Text("正常")
    Integer STATE_NORMAL=1;
    @Text("锁定")
    Integer STATE_LOCKED=2;
    @Text("禁用")
    Integer STATE_FORBIDDEN=3;
}
