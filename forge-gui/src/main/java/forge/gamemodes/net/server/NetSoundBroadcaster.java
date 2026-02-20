package forge.gamemodes.net.server;

import com.google.common.eventbus.Subscribe;
import forge.game.event.GameEvent;
import forge.gui.events.UiEvent;
import forge.player.GamePlayerUtil;
import forge.sound.SoundEffectType;
import forge.sound.EventVisualizer;
import forge.gamemodes.net.event.PlaySoundEvent;

/**
 * Subscribes to the host's game event bus and broadcasts play-sound events to
 * all remote clients so they hear the same sound effects as the host.
 */
public final class NetSoundBroadcaster {
    private final EventVisualizer visualizer = new EventVisualizer(GamePlayerUtil.getGuiPlayer());

    @Subscribe
    public void receiveEvent(final GameEvent evt) {
        final SoundEffectType effect = evt.visit(visualizer);
        if (effect == null) {
            return;
        }
        if (effect == SoundEffectType.ScriptedEffect) {
            final String resourceName = visualizer.getScriptedSoundEffectName(evt);
            if (resourceName != null && !resourceName.isEmpty()) {
                FServerManager.getInstance().broadcast(new PlaySoundEvent(effect, resourceName, false));
            }
        } else {
            FServerManager.getInstance().broadcast(new PlaySoundEvent(effect, null, effect.isSynced()));
        }
    }

    @Subscribe
    public void receiveEvent(final UiEvent evt) {
        final SoundEffectType effect = evt.visit(visualizer);
        if (effect != null) {
            FServerManager.getInstance().broadcast(new PlaySoundEvent(effect, null, effect.isSynced()));
        }
    }
}
