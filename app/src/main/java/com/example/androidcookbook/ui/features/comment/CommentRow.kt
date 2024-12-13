package com.example.androidcookbook.ui.features.comment

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.domain.model.post.Comment
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.common.iconbuttons.LikeButton
import com.example.androidcookbook.ui.common.utils.apiDateFormatter
import com.example.androidcookbook.ui.components.post.SmallAvatar
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun CommentRow(
    comment: Comment,
    currentUser: User,
    onDeleteComment: (Comment) -> Unit,
    onEditComment: (Comment) -> Unit,
    onLikeComment: (Comment) -> Unit,
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isContextMenuVisible by remember { mutableStateOf(false) }
//    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    val localDensity = LocalDensity.current

    var offset by remember { mutableStateOf(Offset.Zero) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .indication(interactionSource, LocalIndication.current)
            .onSizeChanged {
                itemHeight = with(localDensity) { it.height.toDp() }
            }
            .pointerInteropFilter {
                offset = Offset(it.x, it.y)
                false
            }
            .combinedClickable(
                onClick = {},
                onLongClick = { isContextMenuVisible = true }
            )

    ) {
        Box(contentAlignment = Alignment.TopStart) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
//                .pointerInput(true) {
//                    detectTapGestures(
//                        onLongPress = {
//                            isContextMenuVisible = true
//                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
//                        },
//                        onPress = {
//                            val press = PressInteraction.Press(it)
//                            interactionSource.emit(press)
//                            tryAwaitRelease()
//                            interactionSource.emit(PressInteraction.Release(press))
//                        }
//                    )
//                }
            ) {
                SmallAvatar(
                    author = comment.user,
                    onUserClick = onUserClick,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .weight(1F)
                ) {
                    Row(
                        modifier = Modifier
                    ) {
                        Text(
                            text = comment.user.name,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .clickable { onUserClick(comment.user) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = LocalDate.parse(comment.createdAt, apiDateFormatter).toString(),
                            fontSize = TextUnit(
                                value = 12f,
                                type = TextUnitType.Sp,
                            ),
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    // Comment Content
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = comment.content, modifier = Modifier.fillMaxSize()
                        )
                    }
                }
//            Spacer(Modifier.width(8.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    LikeButton(
                        isLiked = comment.isLiked,
                        onLikedClick = {
                            onLikeComment(comment)
                        },
                    )
                    Text(
                        text = comment.totalLike.toString(),
                    )
                }
            }
            Box {
                DropdownMenu(
                    expanded = isContextMenuVisible,
                    onDismissRequest = { isContextMenuVisible = false },
//                    offset = pressOffset.copy(
////                            x = pressOffset.x.coerceAtMost(240.dp),
//                        y = pressOffset.y - itemHeight
                    offset = DpOffset(
                        Density(LocalContext.current).run { offset.x.toDp() },
                        Density(LocalContext.current).run { offset.y.toDp() },
                    )
                ) {
                    if (currentUser.id == comment.user.id) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = {
                                isContextMenuVisible = false
                                onEditComment(comment)
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                isContextMenuVisible = false
                                onDeleteComment(comment)
                            },
                        )
                    }
                }
            }
        }

//        DropdownMenu(
//            expanded = isContextMenuVisible,
//            onDismissRequest = { isContextMenuVisible = false },
//            offset = pressOffset.copy(
////                            x = pressOffset.x.coerceAtMost(240.dp),
//                y = pressOffset.y - itemHeight
//            ),
//        ) {
//            if (currentUser.id == comment.user.id) {
//                DropdownMenuItem(
//                    text = { Text("Edit") },
//                    onClick = {
//                        isContextMenuVisible = false
//                        onEditComment(comment)
//                    },
//                )
//                DropdownMenuItem(
//                    text = { Text("Delete") },
//                    onClick = {
//                        isContextMenuVisible = false
//                        onDeleteComment(comment)
//                    },
//                )
//            }
//        }
    }
}