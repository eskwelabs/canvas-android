/*
 * Copyright (C) 2019 - present Instructure, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.instructure.student.mobius.assignmentDetails.submission.picker

import com.instructure.student.mobius.common.ui.UpdateInit
import com.spotify.mobius.First
import com.spotify.mobius.Next

class PickerSubmissionUploadUpdate :
    UpdateInit<PickerSubmissionUploadModel, PickerSubmissionUploadEvent, PickerSubmissionUploadEffect>() {
    override fun performInit(model: PickerSubmissionUploadModel): First<PickerSubmissionUploadModel, PickerSubmissionUploadEffect> {
        return First.first(model)
    }

    override fun update(
        model: PickerSubmissionUploadModel,
        event: PickerSubmissionUploadEvent
    ): Next<PickerSubmissionUploadModel, PickerSubmissionUploadEffect> = when (event) {
        PickerSubmissionUploadEvent.SubmitClicked -> Next.dispatch(setOf(PickerSubmissionUploadEffect.HandleSubmit(model)))
        PickerSubmissionUploadEvent.CameraClicked -> Next.dispatch(setOf(PickerSubmissionUploadEffect.LaunchCamera))
        PickerSubmissionUploadEvent.GalleryClicked -> Next.dispatch(setOf(PickerSubmissionUploadEffect.LaunchGallery))
        PickerSubmissionUploadEvent.VideoClicked -> Next.dispatch(setOf(PickerSubmissionUploadEffect.LaunchVideoRecorder))
        PickerSubmissionUploadEvent.AudioClicked -> Next.dispatch(setOf(PickerSubmissionUploadEffect.LaunchAudioRecorder))
        PickerSubmissionUploadEvent.SelectFileClicked -> Next.dispatch(setOf(PickerSubmissionUploadEffect.LaunchSelectFile))
        is PickerSubmissionUploadEvent.OnFileSelected -> Next.dispatch(setOf(PickerSubmissionUploadEffect.LoadFileContents(event.uri, model.allowedExtensions)))
        is PickerSubmissionUploadEvent.OnFileRemoved -> {
            val files = model.files.toMutableList()
            files.removeAt(event.fileIndex)
            Next.next(model.copy(files = files.toList()))
        }
        is PickerSubmissionUploadEvent.OnFileAdded -> {
            val files = model.files.toMutableList()
            files.add(event.file)
            Next.next(model.copy(files = files.toList()))
        }
    }
}