package app.morphe.patches.reddit.banner

import app.morphe.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.smali.ExternalLabel
import app.morphe.patches.reddit.shared.Constants.COMPATIBILITY_REDDIT_2026_10_0

private const val PERMANENT_BAN_TEXT = "This account has been banned permanently."

@Suppress("unused")
val hidePermanentBanBannerPatch = bytecodePatch(
    name = "Hide permanent ban banner",
    description = "Hides Reddit's persistent permanent-ban account banner without changing account state or API behavior."
) {
    compatibleWith(COMPATIBILITY_REDDIT_2026_10_0)

    execute {
        HomePermanentBanBannerFingerprint.method.apply {
            val renderBannerIndex = HomePermanentBanBannerFingerprint.instructionMatches[1].index

            addInstructionsWithLabels(
                renderBannerIndex,
                """
                    const-string v1, "$PERMANENT_BAN_TEXT"
                    invoke-virtual { v4, v1 }, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
                    move-result v1
                    if-nez v1, :hidePermanentBanBanner
                """,
                ExternalLabel("hidePermanentBanBanner", getInstruction(renderBannerIndex + 1)),
            )
        }

        InboxPermanentBanBannerFingerprint.method.apply {
            val bannerTextIndex = InboxPermanentBanBannerFingerprint.instructionMatches[0].index
            val continueIndex = bannerTextIndex + 1
            val pagerSetupIndex = InboxPermanentBanBannerFingerprint.instructionMatches[2].index

            addInstructionsWithLabels(
                continueIndex,
                """
                    const-string v4, "$PERMANENT_BAN_TEXT"
                    invoke-virtual { v0, v4 }, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
                    move-result v4
                    if-eqz v4, :continuePermanentBanBanner
                    goto :hidePermanentBanBanner
                """,
                ExternalLabel("continuePermanentBanBanner", getInstruction(continueIndex)),
                ExternalLabel("hidePermanentBanBanner", getInstruction(pagerSetupIndex)),
            )
        }
    }
}
