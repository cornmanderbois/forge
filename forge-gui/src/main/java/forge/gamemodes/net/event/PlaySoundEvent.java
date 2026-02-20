package forge.gamemodes.net.event;

import forge.gamemodes.net.server.RemoteClient;
import forge.sound.SoundEffectType;

/**
 * Network event to play a sound on all clients so that non-host players hear
 * sound effects in multiplayer (the host's game drives sounds; clients don't
 * receive game events, so the host broadcasts this instead).
 */
public final class PlaySoundEvent implements NetEvent {
    private static final long serialVersionUID = 1L;

    private final SoundEffectType soundEffectType;
    /** Non-null only when soundEffectType is ScriptedEffect. */
    private final String scriptedResourceName;
    private final boolean isSynchronized;

    public PlaySoundEvent(final SoundEffectType soundEffectType, final String scriptedResourceName, final boolean isSynchronized) {
        this.soundEffectType = soundEffectType;
        this.scriptedResourceName = scriptedResourceName;
        this.isSynchronized = isSynchronized;
    }

    public SoundEffectType getSoundEffectType() {
        return soundEffectType;
    }

    public String getScriptedResourceName() {
        return scriptedResourceName;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    @Override
    public void updateForClient(final RemoteClient client) {
        // Same sound for all clients; no per-client customization.
    }
}
