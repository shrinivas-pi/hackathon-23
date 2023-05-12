package com.hackathon.android.dynamic_ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.*
import com.hackathon.android.dynamic_ui.ui.theme.DarkerBlue
import com.hackathon.android.dynamic_ui.ui.theme.LightGray

@ExperimentalMaterialApi
@Composable
fun Container(
    modifier: Modifier = Modifier,
    placeHolderText: String?,
    leftIcon: UiElement?,
    rightIcon: UiElement?,
    rightSecondIcon: UiElement?,
    element: UiElement?,
    properties: ComposeProperties?,
    onIcGpsClicked: () -> Unit
) {
    val mContext = LocalContext.current
    AppSearchField(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(properties?.radius ?: 8.dp),
        color = properties?.backgroundColor ?: LightGray,
        onClick = {
            performClick(mContext, properties?.isTapabble, element?.id)
        },
        contentStart = {
            leftIcon?.let {
                BuildImageView(element = leftIcon)
            }
        },
        contentEnd = {
            rightSecondIcon?.let {
                BuildImageView(element = rightSecondIcon)
            }
            rightIcon?.let {
                BuildImageView(element = rightIcon)
            }
        }
    ) {
        placeHolderText?.let {
            SearchFieldText(
                text = it,
                style = TextStyle(
                    fontSize = properties?.textStyle?.fontSize ?: 16.sp
                ),
                color = properties?.foregroundColor ?: DarkerBlue
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun AppSearchField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape,
    color: Color,
    elevation: Dp = 2.dp,
    border: BorderStroke? = null,
    onClick: () -> Unit,
    contentStart: @Composable () -> Unit,
    contentEnd: @Composable (() -> Unit)? = null,
    mainContent: @Composable () -> Unit
) {
    AppSurface(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        color = color,
        elevation = elevation,
        border = border,
        onClick = onClick,
        contentColor = LightGray
    ) {
        AppSearchFieldContent(
            contentStart = contentStart,
            mainContent = mainContent,
            contentEnd = contentEnd,
        )
    }
}

@Composable
fun SearchFieldText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DarkerBlue,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    maxLines: Int = 1,
    style: TextStyle
) {
    Crossfade(targetState = text) {
        AppText(
            modifier = modifier,
            text = it,
            color = color,
            textDecoration = textDecoration,
            textAlign = textAlign,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            style = style
        )
    }
}

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = DarkerBlue,
    textDecoration: TextDecoration? = null,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = TextStyle(
        fontSize = 14.sp,
        letterSpacing = 0.017_857_142_9.sp,
        lineHeight = 18.sp
    )
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        textDecoration = textDecoration,
        fontWeight = fontWeight,
        textAlign = textAlign,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        style = style
    )
}

@ExperimentalMaterialApi
@Composable
fun AppSurface(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape,
    color: Color,
    contentColor: Color,
    border: BorderStroke? = null,
    elevation: Dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        color = color,
        contentColor = contentColor,
        border = border,
        elevation = elevation,
        interactionSource = interactionSource,
        content = content
    )
}

@Composable
private fun AppSearchFieldContent(
    contentStart: @Composable () -> Unit,
    contentEnd: @Composable (() -> Unit)? = null,
    mainContent: @Composable () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier) {
        val (
            startContentRef,
            mainContentRef,
            endContentRef
        ) = createRefs()

        createHorizontalChain(
            startContentRef,
            mainContentRef,
            endContentRef,
            chainStyle = ChainStyle.SpreadInside
        )

        ContentStart(
            ref = startContentRef,
            content = contentStart,
        )
        MainContent(
            startContentRef = startContentRef,
            mainContentRef = mainContentRef,
            endContentRef = endContentRef,
            content = mainContent
        )

        contentEnd?.let { content ->
            ContentEnd(
                ref = endContentRef,
                content = content
            )
        }
    }
}

@Composable
private fun ConstraintLayoutScope.ContentStart(
    ref: ConstrainedLayoutReference,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .constrainAs(ref) {
                centerVerticallyTo(parent)
                start.linkTo(anchor = parent.start)
            }
    ) {
        content()
    }
}

@Composable
private fun ConstraintLayoutScope.MainContent(
    startContentRef: ConstrainedLayoutReference,
    mainContentRef: ConstrainedLayoutReference,
    endContentRef: ConstrainedLayoutReference,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .constrainAs(mainContentRef) {
                width = Dimension.fillToConstraints
                linkTo(
                    top = parent.top,
                    bottom = parent.bottom,
                    end = endContentRef.start,
                    start = startContentRef.end,
                    horizontalBias = 0f,
                )
            }
    ) {
        content()
    }
}

@Composable
private fun ConstraintLayoutScope.ContentEnd(
    ref: ConstrainedLayoutReference,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .constrainAs(ref) {
                centerVerticallyTo(parent)
                end.linkTo(anchor = parent.end)
            }
    ) {
        content()
    }
}