package cn.tzy.wenda.aync;

import java.util.List;

/**
 * Created by tuzhenyu on 17-7-26.
 * @author tuzhenyu
 */
public interface EventHandler {
    void doHandler(EventModel model);

    List<EventType> getSupportEventTypes();
}
