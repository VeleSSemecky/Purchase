package com.veles.purchase.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun Fragment.findParentNavController() =
    requireParentFragment().requireParentFragment().findNavController()

fun Fragment.launchRepeatOnLifecycle(
    state: Lifecycle.State,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = viewLifecycleOwner.lifecycleScope.launch(context, start) {
    viewLifecycleOwner.repeatOnLifecycle(state) {
        block(this)
    }
}

fun CoroutineScope.launchOnError(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    catch: suspend CoroutineScope.(Exception) -> Unit = { },
    block: suspend CoroutineScope.() -> Unit
): Job = launch(context, start) {
    try {
        block(this)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        catch(e)
    }
}

fun Fragment.onCreateComposeView(content: @Composable () -> Unit) =
    ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent { content() }
    }
