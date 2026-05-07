package app.morphe.patches.reddit.banner

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal object HomePermanentBanBannerFingerprint : Fingerprint(
    definingClass = "Lcom/reddit/feedslegacy/switcher/impl/homepager/compose/HomePagerScreen;",
    name = "O5",
    returnType = "V",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf("Landroidx/compose/ui/s;", "Landroidx/compose/runtime/l;", "I"),
    filters = listOf(
        methodCall(
            opcode = Opcode.INVOKE_VIRTUAL,
            smali = "Lip1/c;->j(Landroid/content/res/Resources;)Ljava/lang/String;"
        ),
        methodCall(
            opcode = Opcode.INVOKE_STATIC,
            smali = "Lm22/e;->i(IILandroidx/compose/runtime/l;Landroidx/compose/ui/s;Ljava/lang/String;Lun3/k;Z)V"
        )
    )
)

internal object InboxPermanentBanBannerFingerprint : Fingerprint(
    definingClass = "Lcom/reddit/notification/impl/ui/pager/InboxTabPagerScreen;",
    name = "u5",
    returnType = "Landroid/view/View;",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf("Landroid/view/LayoutInflater;", "Landroid/view/ViewGroup;"),
    filters = listOf(
        methodCall(
            opcode = Opcode.INVOKE_VIRTUAL,
            smali = "Lip1/c;->j(Landroid/content/res/Resources;)Ljava/lang/String;"
        ),
        methodCall(
            opcode = Opcode.INVOKE_VIRTUAL,
            smali = "Lcom/reddit/screen/RedditComposeView;->setContent(Lun3/n;)V"
        ),
        methodCall(
            opcode = Opcode.INVOKE_VIRTUAL,
            smali = "Lcom/reddit/notification/impl/ui/pager/InboxTabPagerScreen;->B5()Lcom/reddit/screen/widget/ScreenPager;"
        )
    )
)
